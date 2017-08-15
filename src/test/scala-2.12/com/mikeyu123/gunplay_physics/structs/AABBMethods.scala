package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class AABBMethods extends GraphicsSpec{

  it should "render correct AABB divicion" in {
    AABB(Point(0, 0), Point(8, 8)).divide should equal(
      Set[AABB](
        AABB(Point(4, 4), Point(8, 8)),
        AABB(Point(0, 4), Point(4, 8)),
        AABB(Point(0, 0), Point(4, 4)),
        AABB(Point(4, 0), Point(8, 4))
      )
    )
  }

  it should "render correct AABB center" in {
    AABB(Point(1, 0), Point(3, 2)).getCenter should equal(
      Point(2.0d, 1.0d)
    )
  }

  it should "render correct AABB width" in {
    AABB(Point(1, 0), Point(3.1, 2)).getW should equal(
      2.1d
    )
  }

  it should "render correct AABB height" in {
    AABB(Point(1, 0), Point(3, 5.2)).getH should equal(
      5.2d
    )
  }

  it should "render correct AABB intersection" in {
    AABB(Point(1, 0), Point(3, 2)).intersects(AABB(Point(0, 1), Point(2, 3))) should equal(
      true
    )
  }

  it should "render correct AABB intersection 1" in {
    AABB(Point(0, 0), Point(2, 2)).intersects(AABB(Point(0, 2), Point(2, 3))) should equal(
      true
    )
  }

  it should "render correct AABB intersection 2" in {
    AABB(Point(1, 5), Point(4, 8)).intersects(AABB(Point(1, 6), Point(2, 7))) should equal(
      true
    )
  }


  it should "render correct AABB intersection 3" in {
    AABB(Point(4, 2), Point(7, 5)).intersects(AABB(Point(5, 5), Point(6, 6))) should equal(
      true
    )
  }

  it should "render incorrect AABB intersection" in {
    AABB(Point(0, 0), Point(2, 2)).intersects(AABB(Point(0, 3), Point(2, 5))) should equal(
      false
    )
  }
}
