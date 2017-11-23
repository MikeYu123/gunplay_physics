package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.util.DebugToString

/**
  * Created by mihailurcenkov on 09.07.17.
  */
//FIXME hard dependency on point order
// !!!clockwise order!!!
case class Rectangle(point1: Point, point2: Point, point3: Point, point4: Point) extends GeometryPrimitive{

  def lines: Set[LineSegment] = {
    Set(
      LineSegment(point1, point2),
      LineSegment(point2, point3),
      LineSegment(point3, point4),
      LineSegment(point4, point1)
    )
  }

  def move(dx: Double, dy: Double): Rectangle = {
    move(Vector(dx, dy))
  }

  def move(vector: Vector): Rectangle = {
    Rectangle(point1 + vector, point2 + vector, point3 + vector, point4 + vector)
  }

  def rotate(center: Point, radians: Double): Rectangle = {
    Rectangle(point1.rotate(center, radians), point2.rotate(center, radians), point3.rotate(center, radians), point4.rotate(center, radians))
  }

  def center: Point = {
    point1 + (point3 - point1) / 2
  }

  def points: Set[Point] = {
    Set[Point](point1, point2, point3, point4)
  }

  def aabb: AABB = {
    val p: Set[Point] = points
    val firstMinMax: (Point, Point) = (point1, point1)
    val (a: Point, b: Point) =
      p.foldLeft(firstMinMax)((minMax, p) => (minMax._1.bottemLeftCorner(p), minMax._2.topRightCorner(p)) )
    AABB(a, b)
  }

  def debugToString: String = {
    "Rect(" + center.x + ", " + center.y+")"
  }
  def contains(point: Point): Boolean = {
    val lines = Set(LineSegment(point1, point2), LineSegment(point2, point3))
    lines.forall(_.projectsOn(point))
  }
}