package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet


class IntersectorDetectorMethods extends GraphicsSpec {
  val r0: Rectangle = Rectangle(Point(0, 0), Point(-1, 2), Point(1, 3), Point(2, 1))
  val r1: Rectangle = Rectangle(Point(1, 2), Point(1, 4), Point(2, 4), Point(2, 2))
  val r2: Rectangle = r1.move(1, 1)

  it should "separation line test 0" in {
    val l = r0.lines
    IntersectionDetector.separation(l.head, r1.points) should equal {
      true
    }
  }

  it should "separation line test 1" in {
    val l = r0.lines
    IntersectionDetector.separation(l.last, r2.points) should equal {
      false
    }
  }

  it should "rect-rect intersection test 0" in {
    IntersectionDetector.rectRect(r0, r1) should equal {
      true
    }
  }

  it should "rect-rect intersection test 1" in {
    IntersectionDetector.rectRect(r0, r2) should equal {
      true
    }
  }
}
