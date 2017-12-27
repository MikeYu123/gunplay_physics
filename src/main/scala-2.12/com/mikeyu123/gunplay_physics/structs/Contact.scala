package com.mikeyu123.gunplay_physics.structs

import java.util.UUID

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}
import com.mikeyu123.gunplay_physics.structs.ContactState.ContactState


object Contact {
  def apply(a: PhysicsObject, b: PhysicsObject): Contact =
    Contact(Set(a, b))
}

case class Contact(ab: Set[PhysicsObject], normal: Vector = Vector(0, 0), state: ContactState = ContactState.Default) {

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
    if (b == physicsObject)
      a
    else b
  }

  def swapSubject(old: PhysicsObject, next: PhysicsObject): Contact = {
    ab.find(_.id == old.id).fold(this) {
      (some) =>
        Contact(ab - old + next, normal)
    }
  }

  def hasObject(uUID: UUID): Boolean = {
    ab.exists(_.id == uUID)
  }

  def remove: Contact = {
    Contact(ab, normal, ContactState.Remove)
  }

  def removeA: Contact = {
    Contact(ab, normal, ContactState.RemoveA)
  }

  def removeB: Contact = {
    Contact(ab, normal, ContactState.RemoveB)
  }

  def removeBoth: Contact = {
    Contact(ab, normal, ContactState.RemoveBoth)
  }
}
