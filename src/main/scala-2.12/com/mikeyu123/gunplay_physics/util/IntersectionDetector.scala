package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.structs.{GeometryPrimitive, LineSegment, Point, Rectangle}

object IntersectionDetector {


  def intersects(a: GeometryPrimitive, b: GeometryPrimitive): Boolean = {

    (a, b) match {
      case (aa: Rectangle, bb: Rectangle) => rectRect(aa, bb)
      case _ => false
    }

    true
  }

  def rectRect(a: Rectangle, b: Rectangle): Boolean = {
    val aLines = a.lines
    val bLines = b.lines
    val aPoints = a.points
    val bPoints = b.points
    val aSep = aLines.exists(separation(_, bPoints))
    val bSep = bLines.exists(separation(_, aPoints))
    aSep || bSep
  }

  def separation(line: LineSegment, points: Set[Point]): Boolean = {

    val vec = line.toVector
    val len = vec.dot(vec)
    val queryVecs = points.map(_ - line.start)
    val dots = queryVecs.map(vec.dot)
    val res = dots.exists { (dot) =>
      0 <= dot && dot <= len
    }
    res
  }
}
