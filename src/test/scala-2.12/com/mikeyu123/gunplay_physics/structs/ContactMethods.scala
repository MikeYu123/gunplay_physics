package com.mikeyu123.gunplay_physics.structs

import java.util.UUID

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.util.ContactHandler
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet
import scala.util.Random

class ContactMethods extends GraphicsSpec {

  val a = PhysicsObjectFactory.spawnPhOb(1)
  val b = PhysicsObjectFactory.spawnPhOb(2)
  val c0 = Contact(a, b)
  val c1 = Contact(b, a)
  val c2 = Contact(a, a)
  val s: Set[Contact] = Set[Contact](c0, c1, c2)

  it should "contact hashCode test 0" in {
    c0.hashCode() == c1.hashCode() should equal {
      true
    }
  }

  it should "contact hashCode test 1" in {
    c0.hashCode() == c2.hashCode() should equal {
      false
    }
  }

  it should "contact hashCode test 2" in {
    c0.hashCode() == c0.hashCode() should equal {
      true
    }
  }

  it should "contact equals test 0" in {
    c0.equals(c1) should equal {
      true
    }
    c1.equals(c0) should equal {
      true
    }
  }

  it should "contact equals test 1" in {
    c0.equals(c2) should equal {
      false
    }
    c2.equals(c0) should equal {
      false
    }
  }

  it should "contact equals test 2" in {
    c0.equals(c0) should equal {
      true
    }
  }

  it should "contact hashset test" in {
    val hs: HashSet[Contact] = HashSet()
    val res = s.foldLeft(hs)(_ + _)
    res.size should equal {
      2
    }
    res should equal {
      HashSet(c0, c2)
    }
  }

  def time[R](block: => R): String = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    "Elapsed time: " + (t1 - t0) + "ns "
  }

  it should "ab order test 00" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "ab order test 01" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnImOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "ab order test 02" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnStOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "ab order test 10" in {
    val m0 = PhysicsObjectFactory.spawnImOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m1)
  }

  it should "ab order test 11" in {
    val m0 = PhysicsObjectFactory.spawnImOb(0)
    val m1 = PhysicsObjectFactory.spawnImOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "ab order test 12" in {
    val m0 = PhysicsObjectFactory.spawnImOb(0)
    val m1 = PhysicsObjectFactory.spawnStOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "ab order test 20" in {
    val m0 = PhysicsObjectFactory.spawnStOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m1)
  }

  it should "ab order test 21" in {
    val m0 = PhysicsObjectFactory.spawnStOb(0)
    val m1 = PhysicsObjectFactory.spawnImOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m1)
  }

  it should "ab order test 22" in {
    val m0 = PhysicsObjectFactory.spawnStOb(0)
    val m1 = PhysicsObjectFactory.spawnStOb(1)
    val c = Contact(m0, m1)
    c.a should equal(m0)
  }

  it should "other test 0" in {
    val m0 = PhysicsObjectFactory.spawnStOb(0)
    val m1 = PhysicsObjectFactory.spawnStOb(1)
    val c = Contact(m0, m1)
    var res = c.other(m0)
    res should equal(m1)
  }

  it should "other test 1" in {
    val m0 = PhysicsObjectFactory.spawnStOb(0)
    val m1 = PhysicsObjectFactory.spawnStOb(1)
    val c = Contact(m0, m1)
    var res = c.other(m1)
    res should equal(m0)
  }

  it should "hasObject test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.hasObject(m0.id) should equal(true)
  }

  it should "hasObject test 1" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.hasObject(m0.id) should equal(true)
  }

  it should "swapObject test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val m2 = PhysicsObjectFactory.spawnPhOb(2)
    val c = Contact(m0, m1)
    c.swapSubject(m1, m2) should equal(Contact(m0, m2))
  }

  it should "swapObject test 1" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val m2 = PhysicsObjectFactory.spawnPhOb(2)
    val c = Contact(m0, m1)
    c.swapSubject(m0, m2) should equal(Contact(m1, m2))
  }

  it should "setNormal test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.setNormal(Vector(1, 0)) should equal(Contact(Set(m0, m1), Vector(1, 0)))
  }

  it should "state test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.removeA should equal(Contact(Set(m0, m1), state = ContactState.RemoveA))
  }

  it should "state test 1" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.removeB should equal(Contact(Set(m0, m1), state = ContactState.RemoveB))
  }

  it should "state test 2" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1)
    val c = Contact(m0, m1)
    c.remove should equal(Contact(Set(m0, m1), state = ContactState.Remove))
  }

  it should "intersects test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0, 0, 0, 0)
    val m1 = PhysicsObjectFactory.spawnPhOb(1, 0, 0, 0)
    val c = Contact(m0, m1)
    c.intersects should equal(true)
  }

  it should "intersects test 1" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0, 0, 0, 0)
    val m1 = PhysicsObjectFactory.spawnPhOb(2, 0, 0, 0)
    val c = Contact(m0, m1)
    c.intersects should equal(false)
  }

  it should "overlaps test 0" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0, 0, 0, 0)
    val m1 = PhysicsObjectFactory.spawnPhOb(2, 0, 0, 0)
    val c = Contact(m0, m1)
    c.overlaps should equal(false)
  }

  it should "overlaps test 1" in {
    val m0 = PhysicsObjectFactory.spawnPhOb(0, 0, 0, 0)
    val m1 = PhysicsObjectFactory.spawnPhOb(0.5, 0.5, 0, 0)
    val c = Contact(m0, m1)
    c.overlaps should equal(true)
  }

}
