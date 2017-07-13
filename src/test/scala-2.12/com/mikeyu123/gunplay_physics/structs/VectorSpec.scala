package com.mikeyu123.gunplay_physics.structs
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

/**
  * Created by mihailurcenkov on 09.07.17.
  */
class VectorSpec extends FlatSpec {
  it should "calculate scalar product correctly" in {
    val vector1 = Vector(3, 4)
    val vector2 = Vector(5, 2)
    (vector1 * vector2) should equal(23)
  }

  it should "calculate product correctly" in {
    val vector1 = Vector(3, 4)
    (vector1 * 2) should equal(Vector(6, 8))
  }

  it should "calculate division correctly" in {
    val vector1 = Vector(3, 4)
    (vector1 / 2) should equal(Vector(1.5, 2))
  }

  it should "calculate pseudoscalar product correctly" in {
    val vector1 = Vector(3, 4)
    val vector2 = Vector(5, 2)
    (vector1 pseudoScalar vector2) should equal(-14)
  }
}
