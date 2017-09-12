package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, Motion, PhysicsProperties, Point, Vector}

case class StaticObject(shape: GeometryPrimitive, center: Point, properties: PhysicsProperties) extends PhysicsObject {

  def move(vector: Vector): PhysicsObject = {
    this
  }

  def rotate(degrees: Double): PhysicsObject = {
    this
  }

  def applyMotion(motion: Motion): PhysicsObject = {
    this
  }

  def applyMotion: PhysicsObject = {
    this
  }

  def setMotion(motion: Motion): PhysicsObject = {
    this
  }
}
