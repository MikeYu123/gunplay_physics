package com.mikeyu123.gunplay_physics.structs

case class PhysicsProperties(contactGroup: Int, movable: Boolean, locked: Boolean) {

  def lock(l: Boolean): PhysicsProperties = {
    PhysicsProperties(contactGroup, movable, l)
  }
}
