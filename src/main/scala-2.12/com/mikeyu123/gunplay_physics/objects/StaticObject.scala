package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

case class StaticObject(shape: GeometryPrimitive, center: Point,
                        properties: PhysicsProperties = PhysicsProperties(Motion(Vector(0, 0), 0)),
                        id: UUID = UUID.randomUUID()) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = this

  def rotate(radians: Double): PhysicsObject = this

  def applyMotion(motion: Motion): PhysicsObject = this

  def applyMotion: PhysicsObject = this

  def setMotion(motion: Motion): PhysicsObject = this
}
