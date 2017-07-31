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

  it should "render correct AABB intersection" in {
    AABB(Point(1, 0), Point(3, 2)).intersects(AABB(Point(0, 1), Point(2, 3))) should equal(
      true
    )
  }
}
