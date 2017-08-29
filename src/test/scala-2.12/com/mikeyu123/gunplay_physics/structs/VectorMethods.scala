package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class VectorMethods extends GraphicsSpec {

  it should "get dot test" in {
    val v0 = Vector(2, 0)
    val v1 = Vector(2, 1)
    v0 * v1 should equal {
      4
    }
  }


  it should "compare test" in {
    Vector(1, 0) > Vector(2, 0) should equal(false)
  }

  it should "compare test 0" in {
    Vector(2, 0) == Vector(2, 0) should equal(true)
  }
}
