package com.mikeyu123.gunplay_physics.structs

object AABB {
  def apply(x0: Double, y0: Double, x1: Double, y1: Double): AABB = AABB(Point(x0, y0), Point(x1, y1))
}

case class AABB(leftBottom: Point, rightTop: Point) {
  def divide: Set[AABB] = {
    val divisionPoint: Point = getCenter
    val q1: AABB = AABB(divisionPoint, rightTop)
    val q3: AABB = AABB(leftBottom, divisionPoint)
    val q2: AABB = AABB(Point(leftBottom.x, divisionPoint.y), Point(divisionPoint.x, rightTop.y))
    val q4: AABB = AABB(Point(divisionPoint.x, leftBottom.y), Point(rightTop.x, divisionPoint.y))
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
    Point((rightTop.x + leftBottom.x) / 2, (rightTop.y + leftBottom.y) / 2)
  }

  def getW: Double = {
    rightTop.x - leftBottom.x
  }

  def getH: Double = {
    rightTop.y - leftBottom.y
  }

  def diagonal: Vector = rightTop - leftBottom

  def intersects(b: AABB): Boolean = {
    val c1: Point = getCenter
    val c2: Point = b.getCenter
    val (dx: Double, dy: Double) = (math.abs(c1.x - c2.x), math.abs(c1.y - c2.y))
    val Vector(dw: Double, dh: Double) = (diagonal + b.diagonal) / 2
    (dx <= dw) && (dy <= dh)
  }
}
