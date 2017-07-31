package com.mikeyu123.gunplay_physics.structs

case class AABB(left_bottom: Point, right_top: Point) {

  def divide: Set[AABB] = {
    val divicion_point: Point = Point((right_top.x + left_bottom.x)/2, (right_top.y + left_bottom.y)/2)
    val q1: AABB = AABB(divicion_point, right_top)
    val q3: AABB = AABB(left_bottom, divicion_point)
    val q2: AABB = AABB(Point(left_bottom.x, divicion_point.y), Point(divicion_point.x, right_top.y))
    val q4: AABB = AABB(Point(divicion_point.x, left_bottom.y), Point(right_top.x, divicion_point.y))
    Set[AABB](q1, q2, q3, q4)
  }

  def intersects(b: AABB): Boolean = {
    val lt: Point = Point(left_bottom.x, right_top.y)
    val rb: Point = Point(right_top.x, left_bottom.y)
    val points: List[Point] = List(right_top, lt, left_bottom, rb)
    points.foreach{p =>
      if ((p.x < b.right_top.x) & (p.x > b.left_bottom.x) &
      (p.y < b.right_top.y) & (p.y > b.left_bottom.y)) return true
    }
    return false
  }
}
