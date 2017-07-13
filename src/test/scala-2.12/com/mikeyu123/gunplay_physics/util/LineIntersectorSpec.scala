package com.mikeyu123.gunplay_physics.util
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import com.mikeyu123.gunplay_physics.structs.{Point, LineSegment}


/**
  * Created by mihailurcenkov on 11.07.17.
  */
class LineIntersectorSpec extends FlatSpec {
  it should "detect correct intersection point on intersecting two lines" in {
    val line1 = LineSegment(Point(0, 0), Point(1, 1))
    val line2 = LineSegment(Point(0, 1), Point(1, 0))
    LineIntersector.intersectLines(line1, line2) should equal(Some(Point(.5d, .5d)))
  }

  it should "detect no intersection point on two parallel lines" in {
    val line1 = LineSegment(Point(0, 0), Point(1, 1))
    val line2 = LineSegment(Point(2, 2), Point(3, 3))
    LineIntersector.intersectLines(line1, line2) should equal(None)
  }
}
