package com.mikeyu123.gunplay_physics.structs

object ContactState extends Enumeration{
  type ContactState = Value
  val default, remove, removeA, removeB = Value
}
