package com.mikeyu123.gunplay_physics.util

import java.util.UUID

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

import scala.collection.immutable.HashSet
//import scala.collection.Set

object ContactHandler {

  def handle(objs: Set[PhysicsObject], aabb: AABB, capacity: Int, depth: Int, contactListener: ContactListener): Set[PhysicsObject] = {
    val updatedObjects = objs.map(_.applyMotion)
    val tree = QTreeBuilder(updatedObjects, aabb, capacity, depth)
    val aabbContacts = getAabbContacts(tree)
    val geometryContacts = getGeometryContacts(aabbContacts)
    val presolvedContacts = geometryContacts.map(contactListener.preSolve)
    val handler0 = ContactHandler(updatedObjects, presolvedContacts).clear
    val correctionQueue = getCorrectionsQueue(handler0.contacts)
    val correctedObjects = correctionQueue.mergeCorrections.applyCorrections
    val handler1 = handler0.merge(correctedObjects).mapContacts(contactListener.postSolve)
    handler1.objs
  }

  def getAabbContacts(qTree: QTree): HashSet[Contact] = {
    qTree.foldLeft(HashSet[Contact]()) { (setOfContacts, leafObjects) =>
      val con = getAabbContactsFromLeaf(leafObjects)
      setOfContacts ++ con
    }
  }

  def getAabbContactsFromLeaf(set: Set[PhysicsObject]): Set[Contact] = {
    val combinations = getCombinations(set)
    combinations.foldLeft(Set[Contact]()) {
      (set, comb) =>
        aabbContact(comb._1, comb._2) match {
          case c: Some[Contact] => set + c.get
          case _ => set
        }
    }
  }

  def aabbContact(a: PhysicsObject, b: PhysicsObject): Option[Contact] = {
    if (a.getAabb.intersects(b.getAabb))
      Option[Contact](Contact(a, b))
    else None
  }

  def getGeometryContacts(aabbContacts: Set[Contact]): Set[Contact] = {
    aabbContacts.filter(IntersectionDetector.intersects)
  }

  def subtract(list: List[PhysicsObject], obj: PhysicsObject): List[PhysicsObject] = {
    list.head match {
      case `obj` => list.tail
      case _ => subtract(list.tail, obj)
    }
  }

  def getCombinations(set: Set[PhysicsObject]): Set[Tuple2[PhysicsObject, PhysicsObject]] = {
    val list = set.toList
    val pairs = for {
      phob0 <- list
      subset = subtract(list, phob0)
      phob1 <- subset
    } yield
      (phob0, phob1)
    pairs.toSet
  }

  def getCorrectionsQueue(geometryContacts: Set[Contact]): CorrectionQueue = {
    val corrections = geometryContacts.map(ContactSolver.solve).reduceLeft(_ ++ _)
    CorrectionQueue(corrections)
  }
}

case class ContactHandler(objs: Set[PhysicsObject], contacts: Set[Contact]) {

  def objectMap: Map[UUID, PhysicsObject] = {
    objs.map {
      obj =>
        (obj.id, obj)
    }.toMap
  }

  def contactMap: Map[UUID, Set[Contact]] = {
    contacts.foldLeft(Map[UUID, Set[Contact]]()) {
      (map, contact) =>
        contact.ab.foldLeft(map) {
          (map, obj) =>
            map.get(obj.id) match {
              case set: Some[Set[Contact]] =>
                map.updated(obj.id, set.get + contact)
              case _ =>
                map.updated(obj.id, Set(contact))
            }
        }
    }
  }

  def clear: ContactHandler = {
    val removedObjects = contacts.collect {
      case c if c.state == ContactState.removeA => c.a.id
      case c if c.state == ContactState.removeB => c.b.id
    }
    val removedContacts: Set[Contact] = removedObjects.size match {
      case 0 => contacts.filter(_.state != ContactState.default)
      case _ => contacts.filter(_.state != ContactState.default) ++ removedObjects.map(contactMap(_)).reduceLeft(_ ++ _)
    }
    val newContacts = contacts -- removedContacts
    val newObjects = (objectMap -- removedObjects).values.toSet
    ContactHandler(newObjects, newContacts)
  }

  def merge(newObjs: Set[PhysicsObject]): ContactHandler = {
    val newObjects = newObjs.foldLeft(objectMap) {
      (map, obj) =>
        map.updated(obj.id, obj)
    }
    val newContacts = contacts.map {
      contact =>
        val a = newObjects(contact.a.id)
        val b = newObjects(contact.b.id)
        Contact(Set(a, b), contact.normal, contact.state)
    }
    ContactHandler(newObjects.values.toSet, newContacts)
  }

  def mapContacts(f: Contact => Contact): ContactHandler = {
    val newContacts = contacts.map(f)
    ContactHandler(objs, newContacts).clear
  }
}
