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
  def apply(a: PhysicsObject, b: PhysicsObject): Contact =
    Contact(Set(a, b), Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), -1)

  def apply(a: PhysicsObject, b: PhysicsObject, state: Int): Contact =
    Contact(Set(a, b), Point(0, 0), LineSegment(Point(0, 0), Point(0, 0)), state)
}

case class Contact(ab: Set[PhysicsObject], contactPoint: Point, normal: LineSegment, state: Int) {
  def a: PhysicsObject = {
    ab.head
  }

  def b: PhysicsObject = {
    ab.last
  }
}
