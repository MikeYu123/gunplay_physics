package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.structs.{Point, Rectangle, Vector}

/**
  * Created by mihailurcenkov on 11.07.17.
  */
case class GraphicsObject(rectangle: Rectangle, center: Point) {
  def move(dx: Double, dy: Double) : GraphicsObject = {
    move(Vector(dx, dy))
  }

  def move(vector: Vector) : GraphicsObject = {
    GraphicsObject(rectangle.move(vector), center + vector)
  }

  def rotate(degrees: Double) : GraphicsObject = {
    GraphicsObject(rectangle.rotate(center, degrees), center)
  }
}
