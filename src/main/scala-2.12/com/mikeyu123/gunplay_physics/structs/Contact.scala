package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}

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
    Contact(Set(a, b), Vector(0, 0))

  //def apply(a: PhysicsObject, b: PhysicsObject, state: Int): Contact =
  //Contact(Set(a, b),  LineSegment(Point(0, 0), Point(0, 0)), state)
}

case class Contact(ab: Set[PhysicsObject], normal: Vector) {

  val (a, b) = (ab.head, ab.last) match {
    case (a: ImmovableObject, b: MovableObject) => (b, a)
    case (a: StaticObject, b: MovableObject) => (b, a)
    case (a: StaticObject, b: ImmovableObject) => (b, a)
    case (a, b) => (a, b)
  }

//  def a: PhysicsObject = {
//    ab.head
//  }
//
//  def b: PhysicsObject = {
//    ab.last
//  }

  def setNormal(vector: Vector): Contact = {
    Contact(ab, vector)
  }
}
