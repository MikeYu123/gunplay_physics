package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

case class MovableObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties, id: UUID = UUID.randomUUID()) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = {
    MovableObject(shape.move(vector), center + vector, properties, id)
  }

  def rotate(radians: Double): PhysicsObject = {
    MovableObject(shape.rotate(radians, center), center, properties, id)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    MovableObject(shape.move(motion, center), center + motion.path, properties, id)
  }

  def applyMotion: PhysicsObject = {
    this.applyMotion(properties.motion)
  }

  def setMotion(motion: Motion): PhysicsObject = {
    MovableObject(shape, center, properties.setMotion(motion), id)
  }
}
