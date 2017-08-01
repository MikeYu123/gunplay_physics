package com.mikeyu123.gunplay_physics.structs

abstract class GeometryPrimitive(impetus: Vector, angle: Double) {
  def center: Point
  def getAabb: AABB
  def getProjection: GeometryPrimitive
}
