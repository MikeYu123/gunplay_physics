package com.mikeyu123.gunplay_physics.structs

import javax.swing.UIDefaults.LazyInputMap

//Taken from here https://habrahabr.ru/post/147691/
// And here https://habrahabr.ru/post/148325/
/**
  * Created by mihailurcenkov on 10.07.17.
  */

object LineSegment {
  def apply(start: Point, vector: Vector): LineSegment = new LineSegment(start, start + vector)
}

case class LineSegment(start: Point, end: Point) {
  def contains(point: Point): Boolean = {
    //    TODO: decompose to vectors
    val point1point2 = end - start
    val point1point = point - start
    val pointpoint1: Vector = start - point
    val pointpoint2: Vector = end - point
    val condition1 = point1point2.pseudoScalar(point1point) == 0
    val condition2 = pointpoint1 * pointpoint2 <= 0
    condition1 && condition2
  }

  def move(dx: Double, dy: Double): LineSegment = {
    LineSegment(start.move(dx, dy), end.move(dx, dy))
  }

  def toVector: Vector = {
    end - start
  }

  def distance(point: Point): Double = {
    val a = end.y - start.y
    val b = end.x - start.x
    val c = end.x * start.y - end.y * start.x
    val numerator = math.abs(a * point.x - b * point.y + c)
    val denominator = math.sqrt(a * a + b * b)

    numerator / denominator
  }

  def normal: Vector = {
    val vec = toVector
    val norm = Vector(-vec.dy, vec.dx)
    norm / math.sqrt(norm * norm)
  }

  def normal(point: Point): Vector = {
    normal * distance(point)
  }

  def has(point: Point): Boolean = {
    if (point.equals(start) || point.equals(end)) true
    else false
  }

  def projectsOn(point: Point): Boolean = {
    val vec = toVector
    val len = vec * vec
    val queryVec = point - start
    val dot = vec * queryVec
    0 <= dot && dot <= len
  }

  def direction(point: Point): Double = {
    val (v0, v1) = (toVector, point - start)
    v0.pseudoScalar(v1)
  }

  def intersects(lineSegment: LineSegment): Boolean = {
    val d0 = direction(lineSegment.start)
    val d1 = direction(lineSegment.end)
    val d2 = lineSegment.direction(start)
    val d3 = lineSegment.direction(end)

    if (d0 * d1 < 0 || d2 * d3 < 0)
      true
    else if (d0 == 0 && projectsOn(lineSegment.start))
      true
    else if (d1 == 0 && projectsOn(lineSegment.end))
      true
    else if (d2 == 0 && lineSegment.projectsOn(start))
      true
    else if (d3 == 0 && lineSegment.projectsOn(end))
      true
    else
      false
  }

  def intersection(lineSegment: LineSegment): Point = {
    val (a1, a2) = (start.y - end.y, lineSegment.start.y - lineSegment.end.y)
    val (b1, b2) = (start.x - end.x, lineSegment.start.x - lineSegment.end.x)
    val (c1, c2) = (start.x * end.y - start.y * end.x,
      lineSegment.start.x * lineSegment.end.y - lineSegment.start.y * lineSegment.end.x)
    val denominator = b1 * a2 - a1 * b2
    val x = (c1 * b2 - b1 * c2) / denominator
    val y = (c1 * a2 - a1 * c2) / denominator
    Point(x, y)
  }

//
//  def willIntersect0(lineSegment: LineSegment): Boolean = {
//    val intersectionPoint = intersection(lineSegment)
//    val extendedLine = LineSegment(start, intersectionPoint)
//    val c0 = extendedLine.projectsOn(end)
//    val c1 = projectsOn(intersectionPoint)
//    val res = c0 || c1
//    if (res) {
//      val res0 = willIntersect0(lineSegment)
//      print("0")
//    }
//    res
//  }
//
//  def willIntersect1(lineSegment: LineSegment): Boolean = {
//    val d0 = direction(lineSegment.start)
//    val d1 = direction(lineSegment.end)
//    val ss = lineSegment.start - start
//    val se = lineSegment.end - start
//    val pr0 = toVector * ss
//    val pr1 = toVector * se
//    if (d0 * d1 <= 0)
//      !(pr0 < 0 && pr1 < 0)
//    else false
//  }

  def willIntersect(lineSegment: LineSegment): Boolean = {
    val d0 = direction(lineSegment.start)
    val d1 = direction(lineSegment.end)
    val l0 = LineSegment(start, lineSegment.start)
    val d2 = l0.direction(end)
    val d3 = l0.direction(lineSegment.end)
    val l1 = LineSegment(start, lineSegment.end)
    val d4 = l1.direction(end)
    val d5 = l1.direction(lineSegment.start)
    (d0 * d1 <= 0) & (d2 * d3 >= 0) & (d4 * d5 >= 0)
  }
}
