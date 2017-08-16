package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._

object ContactHandler {

  def handle(objs: Set[PhysicsObject]): Set[PhysicsObject] = {

    val updatedObjects = objs.map(_.applyMotion)

//    val qtree: QTree = QTree(objs, )
    Set()
  }

//  def getAabbContacts(objs: Set[PhysicsObject]): Set[Contact]={
//
//  }

}
