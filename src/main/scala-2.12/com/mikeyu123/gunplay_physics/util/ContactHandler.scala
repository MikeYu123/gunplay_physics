package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

object ContactHandler {

  def handle(objs: Set[PhysicsObject]): Set[PhysicsObject] = {

    val updatedObjects = objs.map(_.applyMotion)

    //    val qtree: QTree = QTree(objs, )
    Set()
  }

  def getContacts(qTree: QTree): Set[Contact] = {
    val base = Set[Contact]()
    //    qTree.foldLeft(base){    }

    base
  }

  def getAabbContacts(set: Set[PhysicsObject]): Set[Contact] = {
    val combinations = getCombinations(set)
    combinations.foldLeft(Set[Contact]()){
      (set, comb)=>
        aabbContact(comb._1, comb._2) match{
          case c:Some[Contact]=> set + c.get
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

    Set()
  }

  def subtract(set: Set[PhysicsObject], obj: PhysicsObject): Set[PhysicsObject] = {
    if (set.head == obj) set.tail
    else
      subtract(set.tail, obj)
  }

  def getCombinations(set: Set[PhysicsObject]): Set[Tuple2[PhysicsObject, PhysicsObject]] = {
    for {
      phob0 <- set
      subset = subtract(set, phob0)
      phob1 <- subset
    } yield
      (phob0, phob1)
  }

}
