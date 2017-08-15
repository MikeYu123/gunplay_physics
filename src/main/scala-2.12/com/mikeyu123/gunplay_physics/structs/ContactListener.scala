package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

trait ContactListener {

  def perSolve(contact: Contact): Contact

  def PostSolve(contact: Contact): Contact

}
