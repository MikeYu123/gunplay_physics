package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class RectangleMethods extends GraphicsSpec{
it should "render correct AABB" in {
    Rectangle(Point(1, 1), Point(0, 3), Point(4, 5), Point(5, 3)).get_AABB should equal(
      AABB(Point(0,1), Point(5,5))
    )
  }
}
