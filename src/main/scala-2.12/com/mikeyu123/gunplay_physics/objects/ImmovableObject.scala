package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

case class ImmovableObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = {
    ImmovableObject(shape.move(vector), center + vector, properties)
  }

  def rotate(degrees: Double): PhysicsObject = {
    ImmovableObject(shape.rotate(center, degrees), center, properties)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    move(motion.path).rotate(motion.rotation)
  }

  def applyMotion: PhysicsObject = {
    this.applyMotion(properties.motion)
  }

  def setMotion(motion: Motion): PhysicsObject = {
    ImmovableObject(shape, center, properties.setMotion(motion))
  }
}
