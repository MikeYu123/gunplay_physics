package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class PointMethods extends GraphicsSpec {

  it should "get min of 2 points" in {
    Point(1d, 1d).bottemLeftCorner(Point(0d, 2d)) should equal(Point(0d, 1d))
  }

  it should "get max of 2 points" in {
    Point(1d, 1d).topRightCorner(Point(0d, 2d)) should equal(Point(1d, 2d))
  }

  it should "center test" in {
    Point(1d, 1d).center should equal(Point(1d, 1d))
  }

  it should "aabb test" in {
    Point(1d, 1d).aabb should equal(AABB(Point(1d, 1d), Point(1d, 1d)))
  }

  it should "move test 0" in {
    Point(1d, 1d).move(Vector(1, 1)) should equal(Point(2, 2))
  }

  it should "move test 1" in {
    Point(1d, 1d).move(Motion(Vector(1, 1), 0), Point(1, 1)) should equal(Point(2, 2))
  }
}
