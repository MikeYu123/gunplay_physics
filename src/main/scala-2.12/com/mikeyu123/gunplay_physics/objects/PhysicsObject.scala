package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs._

case class PhysicsObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties) {

  def move(vector: Vector): PhysicsObject = {
    PhysicsObject(shape.move(vector), center + vector, properties)
  }

  def rotate(degrees: Double): PhysicsObject = {
    PhysicsObject(shape.rotate(center, degrees), center, properties)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    this.move(motion.path).rotate(motion.rotation)
  }

  def applyMotion: PhysicsObject = {
    if (this.properties.movable)
      this.applyMotion(this.properties.motion)
    else this
  }

  def setMotion(motion: Motion): PhysicsObject={
    if(this.properties.movable)
      PhysicsObject(shape, center, properties.setMotion(motion))
    else this
  }

  def getAabb: AABB = {
    shape.getAabb
  }

  def lock(l: Boolean): PhysicsObject = {
    PhysicsObject(shape, center, properties.lock(l))
  }

  override def toString: String = this.shape.debugToString
}
