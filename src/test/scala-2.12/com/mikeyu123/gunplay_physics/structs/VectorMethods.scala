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

  it should "abs test 0" in {
    Vector(-2, 0).abs should equal(Vector(2, 0))
  }

  it should "project test 0" in {
    Vector(1, 1).project(Vector(3, 2)) should equal(Vector(2, 2))
  }

  it should "project test 1" in {
    val res =  Vector(1, 0).project(Vector(3, 2))
    res should equal(Vector(3, 0))
  }

  it should "project test 2" in {
    val res =  Vector(2, 1).project(Vector(3, 2))
    res should equal(Vector(3, 1.5))
  }

  it should "project test 3" in {
    val res =  Vector(0, 1).project(Vector(3, 2))
    res should equal(Vector(0, 2))
  }
}
