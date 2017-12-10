package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

class CorrectionVectorMethods extends GraphicsSpec {

  it should "reverse test 0" in {
    val vec = CorrectionVector(Vector(1, 1), Vector(23, 0))
    vec.reverseVector.vector should equal(Vector(-1, -1))
  }

  it should "normalize test 0" in {
    val vec = CorrectionVector(Vector(1, 1), Vector(23, 0))
    vec.normalize.normal should equal(Vector(1, 0))
  }
}
