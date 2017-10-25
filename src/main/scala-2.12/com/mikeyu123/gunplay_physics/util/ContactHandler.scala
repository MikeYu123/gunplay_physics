package com.mikeyu123.gunplay_physics.util

import java.util.UUID

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

import scala.collection.immutable.HashSet
import scala.collection.Set

object ContactHandler {

  def handle(objs: Set[PhysicsObject], aabb: AABB, capacity: Int, depth: Int, contactListener: ContactListener): Set[PhysicsObject] = {
    val updatedObjects = objs.map(_.applyMotion)
    val tree = QTreeBuilder(updatedObjects, aabb, capacity, depth)
    val aabbContacts = getAabbContacts(tree)
    val geometryContacts = getGeometryContacts(aabbContacts)

    val presolvedContacts = geometryContacts.map(contactListener.preSolve)
    val filteredContacts = filterContacts(presolvedContacts)

    //    val correctionQueue = getCorrectionsQueue(geometryContacts)
    val correctionQueue = getCorrectionsQueue(filteredContacts)
    val correctedObjects = correctionQueue.mergeCorrections.applyCorrections
    val mergedObjects = mergeUpdates(updatedObjects, correctedObjects)

    //TODO: postSolve
    mergedObjects
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

  def filterContacts(contacts: Set[Contact]): Set[Contact] = {
    val objectMap = contacts.foldLeft(Map[UUID, Set[Contact]]()) {
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
    val removedObjects = contacts.filter(_.state <= -2).map {
      contact =>
        contact.state match {
          case -2 => contact.a.id
          case _ => contact.b.id
        }
    }
    val clearedContacts = removedObjects.foldLeft(contacts) {
      (set, key) =>
        set -- objectMap(key)
    }

    clearedContacts.filter(_.state >= 0)
  }

  def mergeUpdates(objects: Set[PhysicsObject], updated: Set[PhysicsObject]): Set[PhysicsObject] = {
    val map: Map[UUID, PhysicsObject] = objects.map {
      obj =>
        (obj.id, obj)
    }.toMap
    updated.foldLeft(map) {
      (objs, obj) =>
        objs.updated(obj.id, obj)
    }.values.toSet
  }
}
