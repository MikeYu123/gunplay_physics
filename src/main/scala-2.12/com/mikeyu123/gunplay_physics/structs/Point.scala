package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */
case class Point(x: Double, y: Double) {
  def move(dx: Double, dy: Double): Point = {
    Point(x + dx, y + dy)
  }

  def rotate(center: Point, degrees: Double) : Point = {
    val radians = degrees.toRadians
    val newX = center.x + (x - center.x) * Math.cos(radians) - (y - center.y) * Math.sin(radians)
    val newY = center.y + (y - center.y) * Math.cos(radians) + (x - center.x) * Math.sin(radians)
    Point(newX, newY)
  }

  def -(other: Point) = {
    Vector(x - other.x, y - other.y)
  }

  def +(vector: Vector) = {
    Point(x + vector.dx, y + vector.dy)
  }
}