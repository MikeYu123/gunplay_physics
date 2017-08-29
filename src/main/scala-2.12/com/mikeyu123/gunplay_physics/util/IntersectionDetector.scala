package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.structs._

object IntersectionDetector {


  def intersects(contact: Contact): Boolean = intersects(contact.a.shape, contact.b.shape)

  def intersects(a: GeometryPrimitive, b: GeometryPrimitive): Boolean = {

    (a, b) match {
      case (aa: Rectangle, bb: Rectangle) => rectRect(aa, bb)
      case _ => false
    }

    true
  }

  def rectRect(a: Rectangle, b: Rectangle): Boolean = {
    val pointsA = a.points
    val pointsB = b.points
    val sepA = pointsA.map(b.contains)
    if (sepA.reduceLeft(_ || _))
      true
    else {
      val sepB = pointsB.map(a.contains)
      sepB.reduceLeft(_ || _)
    }
  }
}
