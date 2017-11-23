package com.mikeyu123.gunplay_physics.structs

case class GeometryStub(d: Double) extends GeometryPrimitive {
  override def center: Point = ???

  override def aabb: AABB = AABB(d, d, d, d)

  override def move(path: Vector): GeometryPrimitive = ???

  override def rotate(center: Point, angle: Double): GeometryPrimitive = ???

  override def debugToString: String = d.toString
}
