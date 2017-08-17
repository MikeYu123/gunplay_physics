package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet

class ContactMethods extends GraphicsSpec {

  val a = PhysicsObjectFactory.spawnPhOb(1)
  val b = PhysicsObjectFactory.spawnPhOb(2)
  val c0 = Contact(a, b)
  val c1 = Contact(b, a)
  val c2 = Contact(a, a)
  val s: Set[Contact] = Set[Contact](c0, c1, c2)

  it should "contact hashCode test" in {
    c0.hashCode() should equal {
      c1.hashCode()
    }
  }

  it should "contact equals test" in {
    c0.equals(c1) should equal {
      true
    }
  }
  
  it should "contact hashset test" in {
    val hs: HashSet[Contact] = HashSet()
    val res = s.foldLeft(hs)(_ + _)
    res.size should equal {
      2
    }
  }

}
