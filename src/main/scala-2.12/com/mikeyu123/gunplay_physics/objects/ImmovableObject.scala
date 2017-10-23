package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

//object ImmovableObject {
//  def apply(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties, id:UUID): ImmovableObject =
//    new ImmovableObject(shape, center, properties)
//}

case class ImmovableObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties, id: UUID = UUID.randomUUID()) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = {
    ImmovableObject(shape.move(vector), center + vector, properties, id)
  }

  def rotate(degrees: Double): PhysicsObject = {
    ImmovableObject(shape.rotate(center, degrees), center, properties, id)
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    move(motion.path).rotate(motion.rotation)
  }

  def applyMotion: PhysicsObject = {
    this.applyMotion(properties.motion)
  }

  def setMotion(motion: Motion): PhysicsObject = {
    ImmovableObject(shape, center, properties.setMotion(motion), id)
  }
}
