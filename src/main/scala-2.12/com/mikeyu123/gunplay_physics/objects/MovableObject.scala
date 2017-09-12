package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

case class MovableObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = {
    MovableObject(shape.move(vector), center + vector, properties)
  }

  def rotate(degrees: Double): PhysicsObject = {
    MovableObject(shape.rotate(center, degrees), center, properties)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    move(motion.path).rotate(motion.rotation)
  }

  def applyMotion: PhysicsObject = {
    this.applyMotion(properties.motion)
  }

  def setMotion(motion: Motion): PhysicsObject = {
    MovableObject(shape, center, properties.setMotion(motion))
  }
}
