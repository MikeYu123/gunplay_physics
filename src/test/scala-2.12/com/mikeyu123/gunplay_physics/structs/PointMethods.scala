package com.mikeyu123.gunplay_physics.structs
import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

/**
  * Created by mihailurcenkov on 09.07.17.
  */

class PointMethods extends GraphicsSpec {

  it should "get min of 2 points" in {
    Point(1d, 1d).min(Point(0d, 2d)) should equal(Point(0d, 1d))
  }

  it should "get max of 2 points" in {
    Point(1d, 1d).max(Point(0d, 2d)) should equal(Point(1d, 2d))
  }
}
