package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._
import com.mikeyu123.gunplay_physics.util._

import org.scalatest.Matchers._


class ContactHandlerMethods extends GraphicsSpec {

  def spawnPhOb(d: Double): PhysicsObject = {
    val pr = PhysicsProperties(0, true, false, Set(), Motion(Vector(0, 0), 0))
    PhysicsObject(GeometryStub(d), Point(d, d), pr)
  }

  def c(n: Int, k: Int): Int = {
    val nk = (n - k + 1) to n
    val k1 = 1 to k
    val q = nk.product
    val w = k1.product
    q / w
  }

  def c(n: Int): Int = {
    c(n, 2)
  }

  it should "c test" in {
    c(7, 2) should equal {
      c(7)
    }
  }

  it should "combinations test 0" in {
    val set = for (i <- 1 to 7) yield spawnPhOb(i)
    val comb = ContactHandler.getCombinations(set.toSet)
    comb.size should equal {
      21
    }
  }

  it should "combinations test 1" in {
    val set = for (i <- 1 to 10) yield spawnPhOb(i)
    val comb = ContactHandler.getCombinations(set.toSet)
    comb.size should equal {
      c(10)
    }
  }
}
