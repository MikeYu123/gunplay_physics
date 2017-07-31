package com.mikeyu123.gunplay_physics.structs

abstract class GeometryPrimitive {
  def center: Point
  def get_AABB: AABB
}
