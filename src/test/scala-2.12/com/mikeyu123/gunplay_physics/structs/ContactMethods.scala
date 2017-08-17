package com.mikeyu123.gunplay_physics.structs

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

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }

  def randSet(n: Int): Set[Int] = {

    val r = Random
    (for (i <- 0 until n) yield r.nextInt(n)).toSet
  }

  it should "speed test" in {
    val set = randSet(4).map(PhysicsObjectFactory.spawnPhOb(_))
    val con = ContactHandler.getAabbContacts(set)
    val con0 = con.map(ContactWithSet(_))
    time(con.foldLeft(HashSet[Contact]()) { (a, b) => a + b })
    time(con0.foldLeft(HashSet[ContactWithSet]()) { (a, b) => a + b })
  }
}
