package com.mikeyu123.gunplay_physics.structs

object AABB {
  def apply(x0: Double, y0: Double, x1: Double, y1: Double): AABB = AABB(Point(x0, y0), Point(x1, y1))
}

case class AABB(leftBottom: Point, rightTop: Point) {


  def divide: Set[AABB] = {
    val divisionPoint: Point = center
    val quarter1: AABB = AABB(divisionPoint, rightTop)
    val quarter3: AABB = AABB(leftBottom, divisionPoint)
    val quarter2: AABB = AABB(Point(leftBottom.x, divisionPoint.y), Point(divisionPoint.x, rightTop.y))
    val quarter4: AABB = AABB(Point(divisionPoint.x, leftBottom.y), Point(rightTop.x, divisionPoint.y))
    Set[AABB](quarter1, quarter2, quarter3, quarter4)
  }

  def center: Point = {
    Point((rightTop.x + leftBottom.x) / 2, (rightTop.y + leftBottom.y) / 2)
  }

  def width: Double = {
    rightTop.x - leftBottom.x
  }

  def height: Double = {
    rightTop.y - leftBottom.y
  }

  def diagonal: Vector = rightTop - leftBottom

  def intersects(other: AABB): Boolean = {
    val Vector(dx: Double, dy: Double) = (center - other.center).abs
    val Vector(dw: Double, dh: Double) = (diagonal + other.diagonal).abs / 2
    (dx <= dw) && (dy <= dh)
  }

  def +(other: AABB): AABB = {
    AABB(leftBottom.bottemLeftCorner(other.leftBottom), rightTop.topRightCorner(other.rightTop))
  }
}
