package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

//import scala.runtime.Statics
import scala.util.hashing.MurmurHash3

/*
state:
  0 - aabb
  1 - presolve
  2 - solve
  3 - postsolve
  4 - solved
 */
object Contact {
  def apply(a: PhysicsObject, b: PhysicsObject): Contact = Contact(a, b, Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), -1)

  def apply(a: PhysicsObject, b: PhysicsObject, state: Int): Contact = Contact(a, b, Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), state)
}

case class Contact(a: PhysicsObject, b: PhysicsObject, contactPoint: Point, normal: LineSegment, state: Int) {

  override def equals(o: scala.Any): Boolean = {
    o match {
      case c: Contact => {
        val head = a.equals(c.a) && b.equals(c.b) || a.equals(c.b) && b.equals(c.a)
        val tail = for (i <- 2 until this.productArity) yield this.productElement(i).equals(c.productElement(i))
        tail.foldLeft(head)(_ & _)
      }
      case _ => false
    }
  }

  override def hashCode(): Int = {
    val seed = MurmurHash3.productSeed
    val ar = this.productArity
    val ah = this.productElement(0).##
    val bh = this.productElement(1).##
    val head = if (ah >= bh) Set(ah, bh) else Set(bh, ah)
    val tail = for (i <- 2 until ar) yield this.productElement(i).##
    val mix0 = head.foldLeft(seed)(MurmurHash3.mix)
    val mix1 = tail.foldLeft(mix0)(MurmurHash3.mix)
    MurmurHash3.finalizeHash(mix1, ar)
  }
}
