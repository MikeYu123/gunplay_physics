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
    IntersectionDetector.intersects(r0, r1) should equal {
      true
    }
  }

  it should "rect-rect intersection test 1" in {
    IntersectionDetector.intersects(r0, r2) should equal {
      false
    }
  }

  it should "rect-point intersection test 0" in {
    IntersectionDetector.intersects(r0, Point(0, 0)) should equal {
      false
    }
  }

  it should "rect-rect overlap test 0" in {
    IntersectionDetector.overlaps(r0, r1) should equal {
      true
    }
  }

  it should "rect-rect overlap test 1" in {
    IntersectionDetector.overlaps(r0, r1.move(0, 1)) should equal {
      false
    }
  }
}
