package com.mikeyu123.gunplay_physics.structs

abstract class GeometryPrimitive {
  def center: Point

  def getAabb: AABB

  def move(path: Vector): GeometryPrimitive

  def rotate(center: Point, angle: Double): GeometryPrimitive

  def debugToString: String
}
