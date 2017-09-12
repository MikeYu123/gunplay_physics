package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs._


//abstract class PhysicsObject(val shape: GeometryPrimitive, val center: Point, val properties: PhysicsProperties) {
abstract class PhysicsObject {

  val shape: GeometryPrimitive
  val center: Point
  val properties: PhysicsProperties
  //  def move(vector: Vector): PhysicsObject = {
  //    PhysicsObject(shape.move(vector), center + vector, properties)
  //  }
  //
  //  def rotate(degrees: Double): PhysicsObject = {
  //    PhysicsObject(shape.rotate(center, degrees), center, properties)
  //  }
  //
  //  def applyMotion(motion: Motion): PhysicsObject = {
  //    move(motion.path).rotate(motion.rotation)
  //  }
  //
  //  def applyMotion: PhysicsObject = {
  //    properties.objectType match {
  //      case ObjectType.static => this
  //      case _ => this.applyMotion(properties.motion)
  //    }
  //  }
  //
  //  def setMotion(motion: Motion): PhysicsObject = {
  //    properties.objectType match {
  //      case ObjectType.static => this
  //      case _ => PhysicsObject(shape, center, properties.setMotion(motion))
  //    }
  //  }

  def move(vector: Vector): PhysicsObject

  def rotate(degrees: Double): PhysicsObject

  def applyMotion(motion: Motion): PhysicsObject

  def applyMotion: PhysicsObject

  def getAabb: AABB = {
    shape.getAabb
  }

  override def toString: String = shape.debugToString
}
