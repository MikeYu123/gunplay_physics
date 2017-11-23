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

//  it should "nearest test" in {
//    val p0 = Point(0, 0)
//    val p1 = Point(2, 0)
//    val p2 = Point(3, 0)
//    p0.nearest(p1, p2) should equal(Point(2, 0))
//  }
}
