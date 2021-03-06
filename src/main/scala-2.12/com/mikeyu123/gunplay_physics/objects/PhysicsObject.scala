package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs._


abstract class PhysicsObject {

  val shape: GeometryPrimitive
  val center: Point
  val properties: PhysicsProperties
  val id: UUID

  def move(vector: Vector): PhysicsObject

  def rotate(radians: Double): PhysicsObject

  def applyMotion(motion: Motion): PhysicsObject

  def applyMotion: PhysicsObject

  def setMotion(motion: Motion): PhysicsObject

  def setMotion(path: Vector): PhysicsObject = {
    setMotion(Motion(path, properties.motion.radians))
  }

  def aabb: AABB = {
    shape.aabb
  }

//  def revert(time: Double): PhysicsObject = {
//    move(properties.motion.path.reverse * time)
//  }

  override def toString: String = shape.debugToString
}
