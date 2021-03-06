package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

object MovableObject {
  def apply(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties, id: UUID = UUID.randomUUID()): MovableObject =
    new MovableObject(shape, center, properties, id)
}

class MovableObject(override val shape: GeometryPrimitive, override val center: Point,
                    override val properties: PhysicsProperties, override val id: UUID = UUID.randomUUID()) extends PhysicsObject {

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
