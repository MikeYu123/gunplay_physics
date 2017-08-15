package com.mikeyu123.gunplay_physics.structs

case class PhysicsProperties(contactGroup: Int, movable: Boolean, locked: Boolean,
                             contacts: Set[Contact], motion: Motion) {

  def lock(l: Boolean): PhysicsProperties = {
    PhysicsProperties(contactGroup, movable, l, contacts, motion)
  }

  def setMotion(motion: Motion): PhysicsProperties = {
    PhysicsProperties(contactGroup, movable, locked, contacts, motion)
  }

  def addContact(contact: Contact): PhysicsProperties = {
    PhysicsProperties(contactGroup, movable, locked, contacts + contact, motion)
  }

//  def emptyContact
}
