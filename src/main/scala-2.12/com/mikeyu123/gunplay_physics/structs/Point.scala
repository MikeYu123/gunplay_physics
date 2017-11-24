package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */
case class Point(x: Double, y: Double) extends GeometryPrimitive {

  def center: Point = this

  def aabb: AABB = AABB(this, this)

  def move(path: Vector): Point = {
    Point(x + path.dx, y + path.dy)
  }

  def move(dx: Double, dy: Double): Point = {
    move(Vector(dx, dy))
  }

  def rotate(radians: Double, center: Point): Point = {
    move(Motion(Vector(0,0), radians), center)
  }

  def move(motion: Motion, center: Point): Point = {
    val (cos, sin) = (Math.cos(motion.radians), Math.sin(motion.radians))
    val newX = center.x + (x - center.x) * cos - (y - center.y) * sin + motion.path.dx
    val newY = center.y + (y - center.y) * cos + (x - center.x) * sin + motion.path.dy
    Point(newX, newY)
  }

  def +(vector: Vector) = {
    Point(x + vector.dx, y + vector.dy)
  }

  def -(other: Point) = {
    Vector(x - other.x, y - other.y)
  }

  def bottemLeftCorner(b: Point): Point = { // (2, 5).min((3,4)) = (2, 4)
    Point(Math.min(x, b.x), Math.min(y, b.y))
  }

  def topRightCorner(b: Point): Point = {
    Point(Math.max(x, b.x), Math.max(y, b.y))
  }

  def debugToString: String = ???
}