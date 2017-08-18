package com.mikeyu123.gunplay_physics.structs

trait ContactListener {

  def preSolve(contact: Contact): Contact = contact

  def postSolve(contact: Contact): Contact = contact

}
