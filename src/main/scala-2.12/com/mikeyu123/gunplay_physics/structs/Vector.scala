package com.mikeyu123.gunplay_physics.structs

/**
  * Created by mihailurcenkov on 09.07.17.
  */

case class Vector(dx: Double, dy: Double) extends Ordered[Vector] {
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

  def reverse: Vector = {
    Vector(-dx, -dy)
  }

  def -(that: Vector): Vector = {
    Vector(dx - that.dx, dy - that.dy)
  }

  def compareProjection(that: Vector): Double = {
    val n = this * this
    val d = this * that
    if (n == 0 || n.isNaN)
      0 else {
      if (d == 0 || d.isNaN)
        Double.PositiveInfinity
      else
        math.abs(n / d)
    }
  }

  def isZero: Boolean = {
    dx == 0 && dy == 0
  }

  def normalize: Vector = {
    this / math.sqrt(this * this)
  }

  override def compare(that: Vector): Int = {
    (this * this).compare(that * that)
  }

  def abs: Vector = Vector(dx.abs, dy.abs)

  def project(that: Vector): Vector = {
    val xCoef = if (dx == 0) Double.PositiveInfinity else (that.dx / dx).abs
    val yCoef = if (dy == 0) Double.PositiveInfinity else (that.dy / dy).abs
    if (xCoef < yCoef)
      Vector(dx * xCoef, dy * xCoef)
    else
      Vector(dx * yCoef, dy * yCoef)
  }
}
