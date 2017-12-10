package com.mikeyu123.gunplay_physics.util

import java.util.UUID

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

import scala.collection.immutable.HashSet

object ContactHandler {

  def handle(objs: Set[PhysicsObject], aabb: AABB, capacity: Int, depth: Int, contactListener: ContactListener): ContactHandler = {
    val updatedObjects = objs.map(_.applyMotion)
    val tree = QTreeBuilder(updatedObjects, aabb, capacity, depth)
    val aabbContacts = getAabbContacts(tree)
    val geometryContacts = getGeometryContacts(aabbContacts)
    val presolvedContacts = geometryContacts.map(contactListener.preSolve)
    val handler0 = ContactHandler(updatedObjects, presolvedContacts, tree).clear
    val correctionQueue = getCorrectionsQueue(handler0.contacts)
    val correctedObjects = correctionQueue.mergeCorrections.applyCorrections
    val handler1 = handler0.merge(correctedObjects).mapContacts(contactListener.postSolve).rebuildQTree
    //    handler1.objs
    handler1
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
        aabbContact(comb).fold(set)(set + _)
    }
  }

  def aabbContact(objectPair: ObjectPair): Option[Contact] = {
    aabbContact(objectPair.a, objectPair.b)
  }

  def aabbContact(a: PhysicsObject, b: PhysicsObject): Option[Contact] = {
    if (a.aabb.intersects(b.aabb))
      Option[Contact](Contact(a, b))
    else None
  }

  //TODO: to Contact class
  def getGeometryContacts(aabbContacts: Set[Contact]): Set[Contact] = {
    aabbContacts.filter(IntersectionDetector.intersects)
  }

  def subtract(list: List[PhysicsObject], obj: PhysicsObject): List[PhysicsObject] = {
    list.head match {
      case `obj` => list.tail
      case _ => subtract(list.tail, obj)
    }
  }

  def getCombinations(set: Set[PhysicsObject]): Set[ObjectPair] = {
    val list = set.toList
    val pairs = for {
      phob0 <- list
      subset = subtract(list, phob0)
      phob1 <- subset
    } yield
      ObjectPair(phob0, phob1)
    pairs.toSet
  }

  def getCorrectionsQueue(geometryContacts: Set[Contact]): CorrectionQueue = {
    val corrections = geometryContacts.foldLeft(Set[CorrectionQueueEntry]()) {
      (res, contact) =>
        res ++ ContactSolver.solve(contact)
    }
    CorrectionQueue(corrections)
  }
}

case class ContactHandler(objs: Set[PhysicsObject], contacts: Set[Contact], qTree: QTree) {

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
                map + (obj.id -> (set.get + contact))
              case _ =>
                map + (obj.id -> Set(contact))
            }
        }
    }
  }

  def clear: ContactHandler = {
    val removedObjects = contacts.collect {
      case c if c.state == ContactState.RemoveA => c.a.id
      case c if c.state == ContactState.RemoveB => c.b.id
    }
    val removedContacts: Set[Contact] = removedObjects.size match {
      case 0 => contacts.filter(_.state != ContactState.Default)
      case _ => contacts.filter(_.state != ContactState.Default) ++ removedObjects.map(contactMap(_)).reduceLeft(_ ++ _)
    }
    val newContacts = contacts -- removedContacts
    val newObjects = (objectMap -- removedObjects).values.toSet
    ContactHandler(newObjects, newContacts, qTree)
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
    ContactHandler(newObjects.values.toSet, newContacts, qTree)
  }

  def mapContacts(f: Contact => Contact): ContactHandler = {
    val newContacts = contacts.map(f)
    ContactHandler(objs, newContacts, qTree).clear
  }

  def rebuildQTree: ContactHandler = {
    ContactHandler(objs, contacts, QTreeBuilder(qTree, objs))
  }
}
