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

//  def contains(point: Point): Boolean = {
//    //    TODO: decompose to vectors
//    val point1point2 = end - start
//    val point1point = point - start
//    val pointpoint1: Vector = start - point
//    val pointpoint2: Vector = end - point
//    val condition1 = point1point2.pseudoScalar(point1point) == 0
//    val condition2 = pointpoint1 * pointpoint2 <= 0
//    condition1 && condition2
//  }

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
    point.equals(start) || point.equals(end)
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

  def willIntersect(lineSegment: LineSegment): Boolean = {
    val direction0 = direction(lineSegment.start)
    val direction1 = direction(lineSegment.end)
    val line0 = LineSegment(start, lineSegment.start)
    val direction2 = line0.direction(end)
    val direction3 = line0.direction(lineSegment.end)
    val line1 = LineSegment(start, lineSegment.end)
    val direction4 = line1.direction(end)
    val direction5 = line1.direction(lineSegment.start)
    (direction0 * direction1 <= 0) & (direction2 * direction3 >= 0) & (direction4 * direction5 >= 0)
  }
}
