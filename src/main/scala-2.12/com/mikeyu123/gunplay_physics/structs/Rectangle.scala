package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */
//FIXME hard dependency on point order
case class Rectangle(point1: Point, point2: Point, point3: Point, point4: Point) extends GeometryPrimitive{

  def lines: Set[LineSegment] = {
    Set(
      LineSegment(point1, point2),
      LineSegment(point2, point3),
      LineSegment(point3, point4),
      LineSegment(point4, point1)
    )
  }

  def move(dx: Double, dy: Double) : Rectangle = {
    move(Vector(dx, dy))
  }

  def move(vector: Vector) : Rectangle = {
    Rectangle(point1 + vector, point2 + vector, point3 + vector, point4 + vector)
  }

  def rotate(center: Point, degrees: Double) : Rectangle = {
    Rectangle(point1.rotate(center, degrees), point2.rotate(center, degrees), point3.rotate(center, degrees), point4.rotate(center, degrees))
  }

  def center : Point = {
    point1 + (point3 - point1) / 2
  }

  def points: List[Point] = {
    List[Point](point1, point2, point3, point4)
  }

  def get_AABB: AABB = {
    val p: List[Point] = points
    def min(p0: Point, p1:Point): Point = Point(if (p0.x < p1.x) p0.x else p1.x, if (p0.y < p1.y) p0.y else p1.y)
    def max(p0: Point, p1:Point): Point = Point(if (p0.x > p1.x) p0.x else p1.x, if (p0.y > p1.y) p0.y else p1.y)
    val (a:Point, b:Point) = p.fold((point1, point1):Tuple2[Point, Point]){
      case ((a: Point, b: Point), p: Point) => (min(a, p), max(b, p))
    }
    AABB(a, b)
  }
}