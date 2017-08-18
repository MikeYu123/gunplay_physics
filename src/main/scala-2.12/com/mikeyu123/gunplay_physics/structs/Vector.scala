package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */

case class Vector(dx: Double, dy: Double) {
  def +(other: Vector) = Vector(dx + other.dx, dy + other.dy)

  def *(other: Vector): Double = {
    dx * other.dx + dy * other.dy
  }

  def *(coefficient: Double): Vector = {
    Vector(dx * coefficient, dy * coefficient)
  }

  def /(divisor: Double): Vector = {
    Vector(dx / divisor, dy / divisor)
  }

  def pseudoScalar(other: Vector): Double = {
    dx * other.dy - dy * other.dx
  }

  def dot(that: Vector): Double = {
    this.dx * that.dx + this.dy * that.dy
  }
}
