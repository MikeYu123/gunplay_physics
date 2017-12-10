package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

/**
  * Created by mihailurcenkov on 09.07.17.
  */
class RectangleSpec extends GraphicsSpec {
  implicit val rectangleEquality = rectangleEq
  it should "render correct set of lines" in {
    Rectangle(Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)).lines should equal(
      Set(
        LineSegment(Point(0, 0), Point(0, 1)),
        LineSegment(Point(0, 1), Point(1, 1)),
        LineSegment(Point(1, 1), Point(1, 0)),
        LineSegment(Point(1, 0), Point(0, 0))
      )
    )
  }

  it should "calculate correct center" in {
    Rectangle(Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)).center should equal(
      Point(.5, .5)
    )
  }

  it should "invoke move correctly" in {
    Rectangle(Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)).move(1, 1) should equal(
      Rectangle(Point(1, 1), Point(1, 2), Point(2, 2), Point(2, 1))
    )
  }

  it should "rotate rectangle correctly" in {
    val sqrt2 = Math.sqrt(2)

    val originalRectangle = Rectangle(Point(0, 0), Point(5, 0), Point(5, 5), Point(0, 5))
    val rotatedRectangle = Rectangle(Point(0, 0),
      Point(5 / sqrt2, 5 / sqrt2),
      Point(0, 5 * sqrt2),
      Point(-5 / sqrt2, 5 / sqrt2))

    originalRectangle.rotate(45d.toRadians, Point(0,0)) should equal(rotatedRectangle)
  }
}
