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

    Set()
  }


  def getCombinations(set: Set[PhysicsObject]): Set[Tuple2[PhysicsObject, PhysicsObject]] = {
    for {
      phob0 <- set
      subset = substruct(set, phob0)
      phob1 <- subset
    } yield
      (phob0, phob1)
  }

  def substruct(set: Set[PhysicsObject], obj: PhysicsObject): Set[PhysicsObject] = {
    if (set.head == obj) set.tail
    else
      substruct(set.tail, obj)
  }

  def getGeometryContacts(aabbContacts: Set[Contact]): Set[Contact] = {

    Set()
  }

}
