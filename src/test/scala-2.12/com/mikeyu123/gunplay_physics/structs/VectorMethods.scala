package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class VectorMethods extends GraphicsSpec {

  it should "get dot test" in {
    val v0 = Vector(2, 0)
    val v1 = Vector(2, 1)
    v0.dot(v1) should equal {
      4
    }
  }

}
