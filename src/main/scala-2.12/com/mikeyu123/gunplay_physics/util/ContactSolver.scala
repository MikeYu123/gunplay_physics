package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs
import com.mikeyu123.gunplay_physics.structs.{Contact, LineSegment, Point, Rectangle, Vector, ObjectType}

object ContactSolver {

  def solve(contact: Contact): Set[Tuple2[PhysicsObject, Tuple3[Contact, Vector, Double]]] = {
    val a = contact.a
    val b = contact.b
    (a.properties.objectType, b.properties.objectType) match {
      case (ObjectType.static, ObjectType.static) =>
        Set()
      case (ObjectType.immovable, ObjectType.immovable) =>
        Set((a, (contact, a.properties.motion.path, 0)), (b, (contact, b.properties.motion.path, 0)))
      case (ObjectType.immovable, ObjectType.static) =>
        Set((a, (contact, a.properties.motion.path, 0)))
      case (ObjectType.static, ObjectType.immovable) =>
        Set((b, (contact, b.properties.motion.path, 0)))
      case (ObjectType.movable, ObjectType.static) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        Set((a, (contact, afterContactPathA, contactTime._1)))
      }
      case (ObjectType.static, ObjectType.movable) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathB = contactTime._2 * (contactTime._1 * (b.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set((b, (newContact, afterContactPathB, contactTime._1)))
      }
      case (ObjectType.movable, ObjectType.movable) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        val afterContactPathB = contactTime._2 * (contactTime._1 * (b.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set((a, (newContact, afterContactPathA, contactTime._1)), (b, (newContact, afterContactPathB, contactTime._1)))
      }
      case (ObjectType.movable, ObjectType.immovable) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set((a, (newContact, afterContactPathA, contactTime._1)), (b, (newContact, b.properties.motion.path, contactTime._1)))
      }
      case (ObjectType.immovable, ObjectType.movable) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathB = contactTime._2 * (contactTime._1 * (b.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set((a, (newContact, a.properties.motion.path, contactTime._1)), (b, (newContact, afterContactPathB, contactTime._1)))
      }
      case _ => Set()
    }
  }

  def getContactTime(a: PhysicsObject, b: PhysicsObject): (Double, Vector) = {
    val correctionVectorA = getCorrectionVector(a, b)
    val correctionVectorB = getCorrectionVector(b, a)
    val covergence = a.properties.motion.path - b.properties.motion.path
    val projectionA = (correctionVectorA._1.compareProjection(covergence), correctionVectorA._2)
    val projectionB = (correctionVectorB._1.compareProjection(covergence), correctionVectorB._2)
    val res = if (projectionA._1 < projectionB._1) projectionA else projectionB
    res
  }

  def getCorrectionVector(a: PhysicsObject, b: PhysicsObject): (Vector, Vector) = {
    (a.shape, b.shape) match {
      case (rA: Rectangle, rB: Rectangle) => {
        a.properties.objectType match {
          case _ => {
            val res = getCorrectionVector(rA, rB, a.properties.motion.path)
            (res._1, res._2.normalize)
          }
          case static => (Vector(0, 0), Vector(0, 0))
        }
      }
      case _ => (Vector(0, 0), Vector(0, 0))
    }
  }

  // returns: vector, applied to a, prevents interpenetration of a and b
  def getCorrectionVector(a: Rectangle, b: Rectangle, path: Vector): (Vector, Vector) = {
    val pointsB = b.points.filter(a.contains)
    val res = pointsB.size match {
      case 0 => {
        val res = getCorrectionVector(b, a, path.reverse)
        (res._1.reverse, res._2)
      }
      case _ => {
        val resA = getCorrectionVector(a, pointsB, path)
        val resB = getCorrectionVector(b, a.points, path.reverse)

        if (resA._1 > resB._1) resA else (resB._1.reverse, resB._2)
      }
    }
    res
  }

  //
  //  def getCorrectionVector(rectangle: Rectangle, points: Set[Point], path: Vector): Vector = {
  //    val projections = points.map(LineSegment(_, path))
  //    val corrections = projections.map { pr =>
  //      val sides = rectangle.lines.filter(pr.willIntersect)
  //      val ints = sides.map(_.intersection(pr))
  //      val onRect = ints.filter(rectangle.contains)
  ////      val corrs = onRect.map(pr.start - _)
  //      val corrs = onRect.map(pr.start - _)
  //      reduceVectors(corrs)
  //    }
  //    reduceVectors(corrections)
  //  }

  def getCorrectionVector(rectangle: Rectangle, points: Set[Point], path: Vector): (Vector, Vector) = {
    val projections = points.map(LineSegment(_, path))
    val corrections = projections.map { pr =>
      val sides = rectangle.lines.filter(pr.willIntersect)
      val ints = sides.map(side => (side.intersection(pr), side.toVector))
      val onRect = ints.filter(tuple => rectangle.contains(tuple._1))
      val corrs = onRect.map(tuple => (pr.start - tuple._1, tuple._2))
      reduceVectors(corrs)
    }
    reduceVectors(corrections)
  }

  def reduceVectors(vecs: Set[Tuple2[Vector, Vector]]): Tuple2[Vector, Vector] = {
    vecs.nonEmpty match {
      case true => vecs.reduceLeft { (a, b) =>
        if (a._1 > b._1) a else b
      }
      case _ => (Vector(0, 0), Vector(0, 0))
    }
  }

}
