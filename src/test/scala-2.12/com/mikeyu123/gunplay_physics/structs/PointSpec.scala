package com.mikeyu123.gunplay_physics.structs
import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

/**
  * Created by mihailurcenkov on 09.07.17.
  */

class PointSpec extends GraphicsSpec {

  it should "move using correct dx and dy vector" in {
    Point(1d, 1.5d).move(1d, 1.5d) should equal(Point(2d, 3d))
  }

  it should "rotate point correctly" in {
    implicit val equality = pointEq
    val sqrt2 = Math.sqrt(2)

    val originalPoint = Point(5, 5)
    val rotatedPoint = Point(0, 5 * sqrt2)

    originalPoint.rotate(Point(0,0), 45.0d.toRadians) should equal(rotatedPoint)
  }

  it should "calculate correct sum with vector" in {
    (Point(1d, 1.5d) + Vector(0d, .5d)) should equal(Point(1d, 2d))
  }

  it should "calculate correct subtract from point" in {
    (Point(1d, 1.5d) - Point(0d, .5d)) should equal(Vector(1d, 1d))
  }
}
