package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.util.ContactSolver

import scala.collection.SortedSet

object CorrectionQueue {
  def apply(set: Set[CorrectionQueueEntry]): CorrectionQueue = {
    val map = set.foldLeft(Map[PhysicsObject, SortedSet[Correction]]()) {
      (map, corrEnt) =>
        val newSet: SortedSet[Correction] = map.getOrElse(corrEnt.physicsObject, SortedSet[Correction]()) + corrEnt.correction
        map.updated(corrEnt.physicsObject, newSet)
    }
    CorrectionQueue(map)
  }
}

case class CorrectionQueue(map: Map[PhysicsObject, SortedSet[Correction]]) {

  def getMultipleCorrections: Set[PhysicsObject] = {
    map.filter(_._2.size > 1).keySet
  }

  def update(correctionQueueEntry: CorrectionQueueEntry): CorrectionQueue = {
    val set = map(correctionQueueEntry.physicsObject)
    val newSet = set.find(_.related(correctionQueueEntry.correction)) match {
      case c: Some[Correction] =>
        set - c.get + correctionQueueEntry.correction
      case _ => set

    }
    CorrectionQueue(map.updated(correctionQueueEntry.physicsObject, newSet))
  }

  def updated(physicsObject: PhysicsObject, corrections: SortedSet[Correction]): CorrectionQueue = {
    CorrectionQueue(map.updated(physicsObject, corrections))
  }

  def isFlat: Boolean = {
    !map.values.exists(_.size != 1)
  }

  def applyCorrections: Set[PhysicsObject] = {
    isFlat match {
      case true =>
        map.map {
          (pair) =>
            val physicsObject = pair._1
            val correction = pair._2.head
            correction.correct(physicsObject)
        }.toSet
      case _ =>
        Set()
    }
  }

  def mergeCorrections: CorrectionQueue = {
    val mults = getMultipleCorrections
    mults.foldLeft(this)(_.mergeCorrections(_))
  }

  def mergeCorrections(physicsObject: PhysicsObject): CorrectionQueue = {
    val corrs = map(physicsObject)
    val corrsArray = corrs.toArray
    val newEntries = corrsArray.foldLeft(Set[CorrectionQueueEntry]()) {
      (res: Set[CorrectionQueueEntry], corr: Correction) =>
        val index = corrsArray.indexOf(corr)
        index match {
          case 0 =>
            val newOb = corr.correct(physicsObject).setMotion(corr.afterContactPath)
            res + CorrectionQueueEntry(newOb, corr)
          case _ =>
            val previousCorr = corrsArray(index - 1)
            val previousEntry = res.find(_.correction.equals(previousCorr)).get
            val previousObject = previousEntry.physicsObject

            val otherObject = corr.contact.other(physicsObject)
            val newContact = Contact(previousObject, otherObject)

            val correctionEntries = ContactSolver.solve(newContact)
            val newCorrection = correctionEntries.find(_.physicsObject.equals(previousObject)).get
            val otherCorrection = correctionEntries - newCorrection

            val newObject = newCorrection.correction.correct(previousObject).setMotion(newCorrection.correction.afterContactPath)

            res - previousEntry ++ otherCorrection + CorrectionQueueEntry(newObject, corr)
        }
    }
    val newEntry = newEntries.find(_.physicsObject.id.equals(physicsObject.id)).get

    val otherEntries = newEntries - newEntry

    val delta = newEntry.physicsObject.center - physicsObject.center
    val newCorrection = Correction(newEntry.correction.contact, delta, 0)
    val newSet = SortedSet[Correction](newCorrection)

    val newQueue = otherEntries.foldLeft(this) {
      (queue, entry) =>
        queue.update(entry)
    }.updated(physicsObject, newSet)

    newQueue
  }
}
