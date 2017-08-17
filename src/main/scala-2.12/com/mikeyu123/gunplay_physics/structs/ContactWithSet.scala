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
object ContactWithSet {
  def apply(a: PhysicsObject, b: PhysicsObject): ContactWithSet = ContactWithSet(Set(a,b), Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), -1)

  def apply(a: PhysicsObject, b: PhysicsObject, state: Int): ContactWithSet = ContactWithSet(Set(a,b), Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), state)
  def apply(c: Contact): ContactWithSet = ContactWithSet(c.a, c.b)
}

case class ContactWithSet(ab: Set[PhysicsObject], contactPoint: Point, normal: LineSegment, state: Int) {


}
