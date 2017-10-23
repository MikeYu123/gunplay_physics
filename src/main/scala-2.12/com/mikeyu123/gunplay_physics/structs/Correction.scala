package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class Correction(contact: Contact, afterContactPath: Vector, contactTime: Double) extends Ordered[Correction] {

  def compare(that: Correction): Int = {
    that.contactTime.compare(contactTime)
  }

  def correct(physicsObject: PhysicsObject): PhysicsObject = {
    val path = physicsObject.properties.motion.path * (-contactTime) + afterContactPath
    physicsObject.move(path)
  }

  def related(that: Correction): Boolean = {
    contact.a.id.equals(that.contact.a.id) match {
      case true =>
        contact.b.id.equals(that.contact.b.id)
      case _ => false
    }
  }

  def swapSubject(old: PhysicsObject, next: PhysicsObject): Correction = {
    Correction(contact.swapSubject(old, next), afterContactPath, contactTime)
  }
}
