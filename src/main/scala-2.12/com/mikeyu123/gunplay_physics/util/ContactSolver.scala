package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs
import com.mikeyu123.gunplay_physics.structs.{Contact, LineSegment, Point, Rectangle, Vector}

object ContactSolver {

  def solve(contact: Contact): Int = {
    (contact.a.shape, contact.b.shape) match {
      case (a: Rectangle, b: Rectangle) => solveRectRect(a, b)
      case _ => contact
    }


    0
  }

  def solveRectRect(a: Rectangle, b: Rectangle): Int = {
    val pointsA = a.points.filter(b.contains)
    val pointsB = b.points.filter(a.contains)

    //    val sidesA = a.lines.filter(_.has(pointA))
    //    val sidesB = b.lines.filter(_.has(pointB))
    0
  }

  def getContactTime(a: PhysicsObject, b: PhysicsObject): Double = {
    val correctionVectorA = getCorrectionVector(a, b)
    val correctionVectorB = getCorrectionVector(b, a)

    val covergence = a.properties.motion.path - b.properties.motion.path

    val projectionA = math.abs((correctionVectorA * correctionVectorA) / (correctionVectorA * covergence))
    val projectionB = math.abs((correctionVectorB * correctionVectorB) / (correctionVectorB * covergence))

    val res = if (projectionA < projectionB) projectionA else projectionB
    res
  }

  def getCorrectionVector(a: PhysicsObject, b: PhysicsObject): Vector = {
    (a.shape, b.shape) match {
      case (rA: Rectangle, rB: Rectangle) => getCorrectionVector(rA, rB, a.properties.motion.path)
      case _ => Vector(0, 0)
    }
  }

  // returns: vector, applied to a, prevents interpenetration of a and b
  def getCorrectionVector(a: Rectangle, b: Rectangle, path: Vector): Vector = {
    val pointsB = b.points.filter(a.contains)
    val res = pointsB.size match {
      case 0 => {
        val res = getCorrectionVector(b, a, path.reverse).reverse
        res
      }
      case _ => {
        val resA = getCorrectionVector(a, pointsB, path)
        val resB = getCorrectionVector(b, a.points, path.reverse).reverse
        if (resA > resB) resA else resB
      }
    }
    res
  }

  def getCorrectionVector(rectangle: Rectangle, points: Set[Point], path: Vector): Vector = {
    val projections = points.map(LineSegment(_, path))
    val corrections = projections.map { pr =>
      val sides = rectangle.lines.filter(pr.willIntersect)
      val ints = sides.map(_.intersection(pr))
      val onRect = ints.filter(rectangle.contains)
      val corrs = onRect.map(pr.start - _)
      reduceVectors(corrs)
    }
    reduceVectors(corrections)
  }

  def reduceVectors(vecs: Set[Vector]): Vector = {
    vecs.nonEmpty match {
      case true => vecs.reduceLeft { (a, b) =>
        if (a > b) a else b
      }
      case _ => Vector(0, 0)
    }
  }

}
