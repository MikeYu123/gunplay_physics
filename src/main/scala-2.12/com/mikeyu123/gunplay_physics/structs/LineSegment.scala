package com.mikeyu123.gunplay_physics.structs
//Taken from here https://habrahabr.ru/post/147691/
// And here https://habrahabr.ru/post/148325/
/**
  * Created by mihailurcenkov on 10.07.17.
  */
case class LineSegment(point1: Point, point2: Point) {
  def contains(point: Point) : Boolean = {
//    TODO: decompose to vectors
    val point1point2 = point2 - point1
    val point1point = point - point1
    val pointpoint1: Vector = point1 - point
    val pointpoint2: Vector = point2 - point
    val condition1 = point1point2.pseudoScalar(point1point) == 0
    val condition2 = pointpoint1 * pointpoint2 <= 0
    condition1 && condition2
  }

  def move(dx: Double, dy: Double) : LineSegment = {
    LineSegment(point1.move(dx, dy), point2.move(dx, dy))
  }
}
