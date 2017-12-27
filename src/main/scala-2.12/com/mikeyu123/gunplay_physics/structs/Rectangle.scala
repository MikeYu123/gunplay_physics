package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.util.DebugToString

/**
  * Created by mihailurcenkov on 09.07.17.
  */


object Rectangle {
  def apply(center: Point, width: Double, height: Double): Rectangle = {
    val (w, h) = (width / 2, height / 2)
    Rectangle(center + Vector(-w, -h), center + Vector(-w, h), center + Vector(w, h), center + Vector(w, -h))
  }
}

//FIXME hard dependency on point order
// !!!clockwise order!!!
case class Rectangle(point1: Point, point2: Point, point3: Point, point4: Point) extends GeometryPrimitive {

  def lines: List[LineSegment] = {
    List(
      LineSegment(point1, point2),
      LineSegment(point2, point3),
      LineSegment(point3, point4),
      LineSegment(point4, point1)
    )
  }

  def move(vector: Vector): Rectangle = {
    Rectangle(point1 + vector, point2 + vector, point3 + vector, point4 + vector)
  }

  def move(dx: Double, dy: Double): Rectangle = {
    move(Vector(dx, dy))
  }

  def rotate(radians: Double, center: Point): Rectangle = {
    Rectangle(point1.rotate(radians, center), point2.rotate(radians, center), point3.rotate(radians, center), point4.rotate(radians, center))
  }

  def move(motion: Motion, center: Point): Rectangle = {
    Rectangle(point1.move(motion, center), point2.move(motion, center), point3.move(motion, center), point4.move(motion, center))
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
      p.foldLeft(firstMinMax)((minMax, p) => (minMax._1.bottemLeftCorner(p), minMax._2.topRightCorner(p)))
    AABB(a, b)
  }

  def debugToString: String = {
    "Rect(" + center.x + ", " + center.y + ")"
  }

  override def toString: String = debugToString

  def contains(point: Point): Boolean = {
    lines.dropRight(2).forall { (line) =>
      val pr = line.projectsOn(point)
      0 <= pr && pr <= 1
    }
  }

  def includes(point: Point): Boolean = {
    lines.dropRight(2).forall { (line) =>
      val pr = line.projectsOn(point)
      0 < pr && pr < 1
    }
  }

  def onBorder(point: Point): Boolean = {
    val projections = lines.dropRight(2).map(_.projectsOn(point))
    val contains = projections.forall { (pr) => 0 <= pr && pr <= 1 }
    val res = if (contains) {
      val ex = projections.exists(_ % 1 == 0)
      ex
    } else false
    res
  }
}