package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}

//import scala.runtime.Statics
import scala.util.hashing.MurmurHash3


object Contact {
  def apply(a: PhysicsObject, b: PhysicsObject): Contact =
    Contact(Set(a, b), Vector(0, 0))
}

case class Contact(ab: Set[PhysicsObject], normal: Vector) {

  val (a, b) = (ab.head, ab.last) match {
    case (a: ImmovableObject, b: MovableObject) => (b, a)
    case (a: StaticObject, b: MovableObject) => (b, a)
    case (a: StaticObject, b: ImmovableObject) => (b, a)
    case (a, b) => (a, b)
  }

  def setNormal(vector: Vector): Contact = {
    Contact(ab, vector)
  }

  def other(physicsObject: PhysicsObject): PhysicsObject = {
    //    ab.filter(!physicsObject.equals(_)).head
    ab.find(!physicsObject.equals(_)).get
  }

  def swapSubject(old: PhysicsObject, next: PhysicsObject): Contact = {
    ab.contains(old) match {
      case true => Contact(ab - old + next, normal)
      case _ => this
    }
  }
}
