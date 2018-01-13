package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class RectangleMethods extends GraphicsSpec {

  val r0: Rectangle = Rectangle(Point(0, 0), Point(-1, 2), Point(1, 3), Point(2, 1))
  val r1: Rectangle = Rectangle(Point(1, 2), Point(1, 4), Point(2, 4), Point(2, 2))
  val r2: Rectangle = r1.move(1, 1)

  it should "render correct AABB" in {
    Rectangle(Point(1, 1), Point(0, 3), Point(4, 5), Point(5, 3)).aabb should equal(
      AABB(Point(0, 1), Point(5, 5))
    )
  }

  it should "contains test 0" in {
    r0.contains(Point(1, 2)) should equal {
      true
    }
  }

  it should "contains test 1" in {
    r0.contains(Point(2, 2)) should equal {
      false
    }
  }

  it should "onBorder test 0" in {
    r1.onBorder(Point(1, 3)) should equal {
      true
    }
  }

  it should "onBorder test 1" in {
    r1.onBorder(Point(0, 3)) should equal {
      false
    }
  }

  it should "onBorder test 2" in {
    r1.onBorder(Point(1.5, 3)) should equal {
      false
    }
  }

  it should "onBorder test 3" in {
    r0.onBorder(Point(1, 0.5)) should equal {
      true
    }
  }

  it should "includes test 0" in {
    r0.includes(Point(1, 0.5)) should equal {
      false
    }
  }

  it should "includes test 1" in {
    r0.includes(Point(1, 1)) should equal {
      true
    }
  }


}
