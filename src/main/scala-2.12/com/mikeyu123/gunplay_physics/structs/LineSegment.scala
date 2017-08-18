package com.mikeyu123.gunplay_physics.structs

//Taken from here https://habrahabr.ru/post/147691/
// And here https://habrahabr.ru/post/148325/
/**
  * Created by mihailurcenkov on 10.07.17.
  */
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
}
