package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.structs
import com.mikeyu123.gunplay_physics.util.DebugToString

abstract class GeometryPrimitive extends DebugToString {

  def center: Point

//  val children: Set[GeometryPrimitive]

  def aabb: AABB

  def move(path: Vector): GeometryPrimitive

  def move(dx: Double, dy: Double): GeometryPrimitive

  def move(motion: Motion, center: Point): GeometryPrimitive

  def rotate(radians: Double, center: Point): GeometryPrimitive
}
