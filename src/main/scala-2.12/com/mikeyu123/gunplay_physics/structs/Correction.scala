package com.mikeyu123.gunplay_physics.structs

case class Correction(contact: Contact, afterContactPath: Vector, contactTime: Double) extends Ordered[Correction] {

  def compare(that: Correction): Int = {
    contactTime.compare(that.contactTime)
  }
}
