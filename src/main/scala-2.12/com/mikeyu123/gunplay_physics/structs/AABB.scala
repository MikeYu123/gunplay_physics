package com.mikeyu123.gunplay_physics.structs

case class AABB(left_bottom: Point, right_top: Point) {

  def divide: Set[AABB] = {
    val divisionPoint: Point = getCenter
    val q1: AABB = AABB(divisionPoint, right_top)
    val q3: AABB = AABB(left_bottom, divisionPoint)
    val q2: AABB = AABB(Point(left_bottom.x, divisionPoint.y), Point(divisionPoint.x, right_top.y))
    val q4: AABB = AABB(Point(divisionPoint.x, left_bottom.y), Point(right_top.x, divisionPoint.y))
    Set[AABB](q1, q2, q3, q4)
  }

  //  def intersects(b: AABB): Boolean = {
  //    val lt: Point = Point(left_bottom.x, right_top.y)
  //    val rb: Point = Point(right_top.x, left_bottom.y)
  //    val points: List[Point] = List(right_top, lt, left_bottom, rb)
  //    points.exists{p =>
  //      (p.x <= b.right_top.x) && (p.x >= b.left_bottom.x) &&
  //        (p.y <= b.right_top.y) && (p.y >= b.left_bottom.y)
  //    }
  //  }

  def getCenter: Point = {
    Point((right_top.x + left_bottom.x) / 2, (right_top.y + left_bottom.y) / 2)
  }

  def getW: Double = {
    right_top.x - left_bottom.x
  }

  def getH: Double = {
    right_top.y - left_bottom.y
  }

  def intersects(b: AABB): Boolean = {
    val c1: Point = getCenter
    val c2: Point = b.getCenter
    val (dx: Double, dy: Double) = (math.abs(c1.x - c2.x), math.abs(c1.y - c2.y))
    val (dw: Double, dh: Double) = ((getW + b.getW) / 2, (getH + b.getH) / 2)
    (dx <= dw) && (dy <= dh)
  }
}
