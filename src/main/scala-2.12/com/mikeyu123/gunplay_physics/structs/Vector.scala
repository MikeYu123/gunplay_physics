package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */

case class Vector(dx: Double, dy: Double) {
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
}
