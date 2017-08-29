package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

import scala.collection.immutable.HashSet
import scala.collection.Set

object ContactHandler {

  def handle(objs: Set[PhysicsObject], aabb: AABB, capacity: Int, depth: Int): Set[PhysicsObject] = {
    val updatedObjects = objs.map(_.applyMotion)
    val tree = QTreeBuilder(updatedObjects, aabb, capacity, depth)
    val aabbContacts = getAabbContacts(tree)
    val geometryContacts = getGeometryContacts(aabbContacts)

    //    val qtree: QTree = QTree(objs, )
    Set()
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
      Option[Contact](Contact(a, b, 0))
    else None
  }

  def getGeometryContacts(aabbContacts: Set[Contact]): Set[Contact] = {
    aabbContacts.filter(IntersectionDetector.intersects)
  }

  def subtract(list: List[PhysicsObject], obj: PhysicsObject): List[PhysicsObject] = {
    list.head match{
      case `obj` => list.tail
      case _ => subtract(list.tail, obj)
    }
  }

  def getCombinations(set: Set[PhysicsObject]): Set[Tuple2[PhysicsObject, PhysicsObject]] = {
    val list  = set.toList
    val pairs = for {
      phob0 <- list
      subset = subtract(list, phob0)
      phob1 <- subset
    } yield
      (phob0, phob1)
    pairs.toSet
  }

}
