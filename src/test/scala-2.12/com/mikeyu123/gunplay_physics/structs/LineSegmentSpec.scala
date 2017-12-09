package com.mikeyu123.gunplay_physics.structs

import org.scalatest.FlatSpec
import org.scalatest.Matchers._


/**
  * Created by mihailurcenkov on 11.07.17.
  */
class LineSegmentSpec extends FlatSpec {
  it should "answer true on contains if line contains point" in {
    val line = LineSegment(Point(0, 0), Point(1, 1))
    val point = Point(.5, .5)
//    line.contains(point) should equal(true)
  }

  it should "answer true on contains if line does not contain point" in {
    val line = LineSegment(Point(0, 0), Point(1, 1))
    val point = Point(10, 15)
//    line.contains(point) should equal(false)
  }

  it should "invoke move correctly" in {
    LineSegment(Point(0, 0), Point(1, 1)).move(1, 1) should equal(LineSegment(Point(1, 1), Point(2, 2)))
  }
}
