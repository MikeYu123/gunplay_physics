package com.mikeyu123.gunplay_physics.structs

case class GeometryStub(d: Double) extends GeometryPrimitive {

  override def center: Point = ???

  override def aabb: AABB = AABB(d, d, d, d)

  override def move(path: Vector): GeometryPrimitive = ???

  override def rotate(angle: Double, center: Point): GeometryPrimitive = ???

  override def debugToString: String = d.toString

  override def move(motion: Motion, center: Point): GeometryPrimitive = ???

//  override val children: Set[GeometryPrimitive] = Set()

  override def move(dx: Double, dy: Double): GeometryPrimitive = ???
}
