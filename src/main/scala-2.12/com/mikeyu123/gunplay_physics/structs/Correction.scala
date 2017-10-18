package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class Correction(contact: Contact, afterContactPath: Vector, contactTime: Double) extends Ordered[Correction] {

  def compare(that: Correction): Int = {
    that.contactTime.compare(contactTime)
  }

  def correct(physicsObject: PhysicsObject): PhysicsObject = {
    val path = physicsObject.properties.motion.path * (1.0 - contactTime) + afterContactPath
    physicsObject.move(path)
  }

  def related(that: Correction): Boolean ={
    contact.ab.equals(that.contact.ab)
  }
}
