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

  it should "subtract test 0" in {
    Vector(2, 0) - Vector(2, 1) should equal(Vector(0, -1))
  }

  it should "compare projection test 0" in {
    Vector(2, 0).compareProjection(Vector(1, 1)) should equal(2)
  }

  it should "is zero test 0" in {
    Vector(2, 0).isZero should equal(false)
  }

  it should "is zero test 1" in {
    Vector(0, 0).isZero should equal(true)
  }

  it should "compare projection test 1" in {
    Vector(2, 0).compareProjection(Vector(4, 1)) should equal(1.0 / 2.0)
  }

  it should "normalize test 0" in {
    Vector(2, 0).normalize should equal(Vector(1, 0))
  }
}
