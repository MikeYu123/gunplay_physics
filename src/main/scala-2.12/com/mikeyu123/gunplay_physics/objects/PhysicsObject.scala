package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs._


//abstract class PhysicsObject(val shape: GeometryPrimitive, val center: Point, val properties: PhysicsProperties) {
abstract class PhysicsObject {

  val shape: GeometryPrimitive
  val center: Point
  val properties: PhysicsProperties

  val id: UUID

  def move(vector: Vector): PhysicsObject

  def rotate(degrees: Double): PhysicsObject

  def applyMotion(motion: Motion): PhysicsObject

  def applyMotion: PhysicsObject

  def setMotion(motion: Motion): PhysicsObject

  def setMotion(path: Vector): PhysicsObject = {
    setMotion(Motion(path, properties.motion.rotation))
  }

  def getAabb: AABB = {
    shape.getAabb
  }

  def revert(time: Double): PhysicsObject = {
    move(properties.motion.path.reverse * time)
  }

  override def toString: String = shape.debugToString
}
