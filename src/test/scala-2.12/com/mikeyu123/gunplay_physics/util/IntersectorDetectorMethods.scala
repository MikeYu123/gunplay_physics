package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet


class IntersectorDetectorMethods extends GraphicsSpec {
  val r0: Rectangle = Rectangle(Point(0, 0), Point(-1, 2), Point(1, 3), Point(2, 1))
  val r1: Rectangle = Rectangle(Point(1, 2), Point(1, 4), Point(2, 4), Point(2, 2))
  val r2: Rectangle = r1.move(1, 1)

  it should "rect-rect intersection test 0" in {
    IntersectionDetector.rectRect(r0, r1) should equal {
      true
    }
  }

  it should "rect-rect intersection test 1" in {
    IntersectionDetector.rectRect(r0, r2) should equal {
      false
    }
  }
}
