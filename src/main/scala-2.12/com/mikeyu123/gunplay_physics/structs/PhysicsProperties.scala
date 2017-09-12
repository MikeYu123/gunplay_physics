package com.mikeyu123.gunplay_physics.structs


case class PhysicsProperties(contactGroup: Int, motion: Motion) {

  def setMotion(motion: Motion): PhysicsProperties = {
    PhysicsProperties(contactGroup, motion)
  }

  //  def emptyContact
}
