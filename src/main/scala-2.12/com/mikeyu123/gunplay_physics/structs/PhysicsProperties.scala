package com.mikeyu123.gunplay_physics.structs

case class PhysicsProperties(motion: Motion) {

  def setMotion(motion: Motion): PhysicsProperties = {
    PhysicsProperties(motion)
  }
}
