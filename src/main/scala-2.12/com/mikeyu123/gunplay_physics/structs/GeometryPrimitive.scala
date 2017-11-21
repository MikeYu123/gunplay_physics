package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.util.DebugToString

abstract class GeometryPrimitive extends DebugToString{
  def center: Point

  def aabb: AABB

  def move(path: Vector): GeometryPrimitive

  def rotate(center: Point, radians: Double): GeometryPrimitive
}
