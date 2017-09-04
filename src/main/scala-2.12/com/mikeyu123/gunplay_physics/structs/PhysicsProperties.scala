package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.structs.ObjectType.ObjectType

object ObjectType extends Enumeration {
  type ObjectType = Value
  val movable, immovable, static = Value
}

case class PhysicsProperties(contactGroup: Int, objectType: ObjectType, motion: Motion) {

  def setMotion(motion: Motion): PhysicsProperties = {
    PhysicsProperties(contactGroup, objectType, motion)
  }

  //  def emptyContact
}
