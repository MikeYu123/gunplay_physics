package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}
import com.mikeyu123.gunplay_physics.structs.{Contact, CorrectionQueueEntry, Correction, LineSegment, Point, Rectangle, Vector, CorrectionVector}

object ContactSolver {

  //  def solve(contact: Contact): Set[CorrectionQueueEntry] = {
  //    val a = contact.a
  //    val b = contact.b
  //
  //    (a, b) match {
  //      case (_: MovableObject, _: MovableObject) => {
  //        val contactTime = getContactTime(a, b)
  //        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
  //        val afterContactPathB = contactTime._2 * (contactTime._1 * (b.properties.motion.path * contactTime._2))
  //        val newContact = contact.setNormal(contactTime._2)
  //        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)),
  //          CorrectionQueueEntry(b, Correction(newContact, afterContactPathB, contactTime._1)))
  //      }
  //      case (_: MovableObject, _: ImmovableObject) => {
  //        val contactTime = getContactTime(a, b)
  //        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
  //        val newContact = contact.setNormal(contactTime._2)
  //        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)),
  //          CorrectionQueueEntry(b, Correction(newContact, b.properties.motion.path, 1)))
  //      }
  //      case (_: MovableObject, _: StaticObject) => {
  //        val contactTime = getContactTime(a, b)
  //        val afterContactPathA = contactTime._2 * (contactTime._1 * (a.properties.motion.path * contactTime._2))
  //        val newContact = contact.setNormal(contactTime._2)
  //        Set(CorrectionQueueEntry(a, Correction(newContact, afterContactPathA, contactTime._1)))
  //      }
  //      case (_: ImmovableObject, _: ImmovableObject) =>
  //        Set(CorrectionQueueEntry(a, Correction(contact, a.properties.motion.path, 1)),
  //          CorrectionQueueEntry(b, Correction(contact, b.properties.motion.path, 1)))
  //      case (_: ImmovableObject, _: StaticObject) =>
  //        Set(CorrectionQueueEntry(a, Correction(contact, a.properties.motion.path, 1)))
  //
  //      case (_: StaticObject, _: StaticObject) =>
  //        Set()
  //
  //      case _ => Set()
  //    }
  //  }

  def apply(a: PhysicsObject, b: PhysicsObject): ContactSolver = ContactSolver(Contact(a, b))

  def solve(contact: Contact): Set[CorrectionQueueEntry] = {
    val a = contact.a
    val b = contact.b
    val solver = new ContactSolver(contact)
    (a, b) match {
      case (_: MovableObject, _: MovableObject) => {
        val solution = solver.getContactTime
        solution.entries(a, b)
      }
      case (_: MovableObject, _: ImmovableObject) |
           (_: MovableObject, _: StaticObject) => {
        solver.getContactTime.entries(a)
      }
      case _ => Set()
    }
  }
}

case class ContactSolver(contact: Contact, contactTime: Double = 1) {

  def afterContactPath(physicsObject: PhysicsObject): Vector = {
    contact.normal * (contactTime * (physicsObject.properties.motion.path * contact.normal))
  }

  def correction(physicsObject: PhysicsObject): Correction = {
    Correction(contact, afterContactPath(physicsObject), contactTime)
  }

  def entries(physicsObjects: PhysicsObject*): Set[CorrectionQueueEntry] = {
    physicsObjects.map(entry).toSet
  }

  def entry(physicsObject: PhysicsObject): CorrectionQueueEntry = {
    CorrectionQueueEntry(physicsObject, correction(physicsObject))
  }

  def getContactTime: ContactSolver = {
    val (a, b) = (contact.a, contact.b)

    val correctionVectorA = getCorrectionVector(a, b)
    val correctionVectorB = getCorrectionVector(b, a)
    val convergence = (a, b) match {
      case (_: MovableObject, _: ImmovableObject) => a.properties.motion.path
      case _ => a.properties.motion.path - b.properties.motion.path
    }

    val (contactTimeA, normalA) = getProjection(correctionVectorA, convergence)
    val (contactTimeB, normalB) = getProjection(correctionVectorB, convergence)
    contactTimeA < contactTimeB match {
      case true => ContactSolver(contact.setNormal(normalA), contactTimeA)
      case false => ContactSolver(contact.setNormal(normalB), contactTimeB)
    }
  }

  def getCorrectionVector(a: PhysicsObject, b: PhysicsObject): CorrectionVector = {
    (a.shape, b.shape) match {
      case (rA: Rectangle, rB: Rectangle) => {
        a match {
          case _: StaticObject => CorrectionVector(Vector(0, 0), Vector(0, 0))
          case _: ImmovableObject => CorrectionVector(Vector(0, 0), Vector(0, 0))
          case _ => {
            val res = getCorrectionVector(rA, rB, a.properties.motion.path)
            res.normalize
          }
        }
      }
      case _ => CorrectionVector(Vector(0, 0), Vector(0, 0))
    }
  }

  def getCorrectionVector(a: Rectangle, b: Rectangle, path: Vector): CorrectionVector = {
    val pointsB = b.points.filter(a.contains)
    val res = pointsB.size match {
      case 0 => {
        getCorrectionVector(b, a, path.reverse).reverseVector
      }
      case _ => {
        val resA = getCorrectionVector(a, pointsB, path)
        val resB = getCorrectionVector(b, a.points, path.reverse).reverseVector
        if (resA >= resB) resA else resB
      }
    }
    res
  }

  def getCorrectionVector(rectangle: Rectangle, points: Set[Point], path: Vector): CorrectionVector = {
    val corrections = points.foldLeft(Set[CorrectionVector]()) { (set, point) =>
      val projection: LineSegment = LineSegment(point, path)
      val intersections: Set[(Point, Vector)] = rectangle.lines.collect {
        case side if projection.willIntersect(side) => (side.intersection(projection), side.toVector)
      }
      val corrs: Set[CorrectionVector] = intersections.collect {
        case int if rectangle.contains(int._1) => CorrectionVector(projection.start - int._1, int._2)
      }
      set ++ corrs
    }
    reduceVectors(corrections)
  }

  def reduceVectors(vecs: Set[CorrectionVector]): CorrectionVector = {
    vecs.nonEmpty match {
      case true => vecs.max
      case _ => CorrectionVector(Vector(0, 0), Vector(0, 0))
    }
  }

  def getProjection(correctionVector: CorrectionVector, convergence: Vector): (Double, Vector) = {
    val time = correctionVector.vector.compareProjection(convergence)
    (time.isInfinite, correctionVector.normal.isZero) match {
      case (true, false) => (0, correctionVector.normal)
      case _ => (time, correctionVector.normal)
    }
  }
}
