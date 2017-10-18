package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}
import com.mikeyu123.gunplay_physics.structs.{Contact, CorrectionQueueEntry, Correction, LineSegment, Point, Rectangle, Vector}

object ContactSolver {

  def solve(contact: Contact): Set[CorrectionQueueEntry] = {
    val a = contact.a
    val b = contact.b

    (a, b) match {
      case (_: MovableObject, _: MovableObject) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        val afterContactPathB = contactTime._2 * (contactTime._1 * (b.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)),
          CorrectionQueueEntry(b, Correction(newContact, afterContactPathB, contactTime._1)))
      }
      case (_: MovableObject, _: ImmovableObject) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)),
          CorrectionQueueEntry(b, Correction(newContact, b.properties.motion.path, 1)))
      }
      case (_: MovableObject, _: StaticObject) => {
        val contactTime = getContactTime(a, b)
        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
        val newContact = contact.setNormal(contactTime._2)
        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)))
      }
      case (_: ImmovableObject, _: ImmovableObject) =>
        Set(CorrectionQueueEntry(a, Correction(contact, a.properties.motion.path, 1)),
          CorrectionQueueEntry(b, Correction(contact, b.properties.motion.path, 1)))
      case (_: ImmovableObject, _: StaticObject) =>
        Set(CorrectionQueueEntry(a, Correction(contact, a.properties.motion.path, 1)))

      case (_: StaticObject, _: StaticObject) =>
        Set()

      case _ => Set()
    }
  }

  def getContactTime(a: PhysicsObject, b: PhysicsObject): (Double, Vector) = {
    val correctionVectorA = getCorrectionVector(a, b)
    val correctionVectorB = getCorrectionVector(b, a)
    val convergence = (a, b) match {
      case (_: MovableObject, _: ImmovableObject) => a.properties.motion.path
      case _ => a.properties.motion.path - b.properties.motion.path
    }

    val projectionA = getProjection(correctionVectorA, convergence)
    val projectionB = getProjection(correctionVectorB, convergence)
    val res = if (projectionA._1 < projectionB._1) projectionA else projectionB
    res
  }

  def getCorrectionVector(a: PhysicsObject, b: PhysicsObject): (Vector, Vector) = {
    (a.shape, b.shape) match {
      case (rA: Rectangle, rB: Rectangle) => {
        a match {
          case _: StaticObject => (Vector(0, 0), Vector(0, 0))
          case _: ImmovableObject => (Vector(0, 0), Vector(0, 0))
          case _ => {
            val res = getCorrectionVector(rA, rB, a.properties.motion.path)
            (res._1, res._2.normalize)
          }
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

  def getProjection(correctionVector: Tuple2[Vector, Vector], convergence: Vector): Tuple2[Double, Vector] = {
    getProjection(correctionVector._1, convergence, correctionVector._2)
  }

  def getProjection(correctionVector: Vector, convergence: Vector, normal: Vector): Tuple2[Double, Vector] = {
    val time = correctionVector.compareProjection(convergence)
    (time.isInfinite, normal.isZero) match {
      case (true, false) => (0, normal)
      case _ => (time, normal)
    }
  }
}
