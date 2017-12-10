package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.structs._

object IntersectionDetector {


  def intersects(contact: Contact): Boolean = intersects(contact.a.shape, contact.b.shape)

  def intersects(a: GeometryPrimitive, b: GeometryPrimitive): Boolean = {
    (a, b) match {
      case (aa: Rectangle, bb: Rectangle) => rectRect(aa, bb)
      case _ => false
    }
  }

  def rectRect(a: Rectangle, b: Rectangle): Boolean = {
    val pointsA = a.points
    val pointsB = b.points
    pointsA.exists(b.contains) || pointsB.exists(a.contains)
  }
}
