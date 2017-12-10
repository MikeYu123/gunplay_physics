package com.mikeyu123.gunplay_physics

/**
  * Created by mihailurcenkov on 10.07.17.
  */
//TODO: add hashCode() implementation
package object structs {

  case class SceneProperties(capacity: Int = 4, depth: Int = 4)

  case class Motion(path: Vector, radians: Double)

}
