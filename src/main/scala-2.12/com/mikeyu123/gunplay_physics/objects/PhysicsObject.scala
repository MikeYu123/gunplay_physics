package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs._

case class PhysicsObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties) {

  def move(dx: Double, dy: Double): PhysicsObject = {
    move(Vector(dx, dy))
  }

  def move(vector: Vector): PhysicsObject = {
    PhysicsObject(shape.move(vector), center + vector, properties)
  }

  def rotate(degrees: Double): PhysicsObject = {
    PhysicsObject(shape.rotate(center, degrees), center, properties)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    this.move(motion.path).rotate(motion.rotation)
  }

  def getAabb: AABB = {
    shape.getAabb
  }

  def lock(l: Boolean): PhysicsObject = {
    PhysicsObject(shape, center, properties.lock(l))
  }
}
