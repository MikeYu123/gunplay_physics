package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.util.ContactSolver

import scala.collection.SortedSet

object CorrectionQueue {
  def apply(set: Set[CorrectionQueueEntry]): CorrectionQueue = {
    val map = set.foldLeft(Map[PhysicsObject, SortedSet[Correction]]()) {
      (map, correctionEntry) =>
        val oldSet = map.getOrElse(correctionEntry.physicsObject, SortedSet[Correction]())
        val newSet: SortedSet[Correction] = oldSet + correctionEntry.correction
        map + (correctionEntry.physicsObject -> newSet)
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
    val newSet = set.find(_.related(correctionQueueEntry.correction)).fold(set)(set - _ + correctionQueueEntry.correction)
    CorrectionQueue(map + (correctionQueueEntry.physicsObject -> newSet))
  }

  def +(physicsObject: PhysicsObject, corrections: SortedSet[Correction]): CorrectionQueue = {
    CorrectionQueue(map + (physicsObject -> corrections))
  }

  def isFlat: Boolean = {
    !map.values.exists(_.size != 1)
  }

  def applyCorrections: Set[PhysicsObject] = {
    if (isFlat) {
      map.map {
        (pair) =>
          val physicsObject = pair._1
          val correction = pair._2.head
          correction.correct(physicsObject)
      }.toSet
    }
    else Set()
  }

  def mergeCorrections: CorrectionQueue = {
    val mults = getMultipleCorrections
    mults.foldLeft(this)(_.mergeCorrections(_))
  }

  //  def mergeCorrections(physicsObject: PhysicsObject): CorrectionQueue = {
  //    val corrections = map(physicsObject)
  //    val correctionsArray = corrections.toArray
  //    val newEntries = correctionsArray.foldLeft(Set[CorrectionQueueEntry]()) {
  //      (res: Set[CorrectionQueueEntry], correction: Correction) =>
  //        val index = correctionsArray.indexOf(correction)
  //        index match {
  //          case 0 =>
  //            val newOb = correction.correct(physicsObject).setMotion(correction.afterContactPath)
  //            res + CorrectionQueueEntry(newOb, correction)
  //          case _ =>
  //            val previousCorr = correctionsArray(index - 1)
  //            val previousEntry = res.find(_.correction.equals(previousCorr)).get
  //            val previousEntry0 = res.last
  //            val previousObject = previousEntry.physicsObject
  //
  //            val otherObject = correction.contact.other(physicsObject)
  //            val newContact = Contact(previousObject, otherObject)
  //
  //            val correctionEntries = ContactSolver.solve(newContact)
  //            val newCorrection = correctionEntries.find(_.physicsObject.equals(previousObject)).get
  //            val otherCorrection = correctionEntries - newCorrection
  //
  //            val newObject = newCorrection.correction.correct(previousObject).setMotion(newCorrection.correction.afterContactPath)
  //
  //            res - previousEntry ++ otherCorrection + CorrectionQueueEntry(newObject, correction)
  //        }
  //    }
  //    val newEntry = newEntries.find(_.physicsObject.id.equals(physicsObject.id)).get
  //
  //    val otherEntries = newEntries - newEntry
  //
  //    val delta = newEntry.physicsObject.center - physicsObject.center
  //    val newCorrection = Correction(newEntry.correction.contact, delta, 0)
  //    val newSet = SortedSet[Correction](newCorrection)
  //
  //    val newQueue = otherEntries.foldLeft(this) {
  //      (queue, entry) =>
  //        queue.update(entry)
  //    }.updated(physicsObject, newSet)
  //
  //    newQueue
  //  }

  def mergeCorrections(physicsObject: PhysicsObject): CorrectionQueue = {
    val corrections = map(physicsObject)
    val merger = corrections.foldLeft(Merger(CorrectionQueueEntry(physicsObject, corrections.head), Set())) {
      (merger: Merger, correction: Correction) =>
        val previousObject = merger.cumulator.physicsObject
        val otherObject = correction.contact.other(physicsObject)
        val newContact = Contact(previousObject, otherObject)
        if (newContact.intersects) {
          val correctionEntries = ContactSolver.solve(newContact)
          merger.fold(correctionEntries, previousObject, correction)
        } else merger
    }
    val delta = merger.cumulator.physicsObject.center - physicsObject.center
    val newCorrection = Correction(merger.cumulator.correction.contact, delta, 0)
    val newSet = SortedSet[Correction](newCorrection)
    merger.set.foldLeft(this)(_.update(_)) + (physicsObject, newSet)
  }

  case class Merger(cumulator: CorrectionQueueEntry, set: Set[CorrectionQueueEntry]) {

    def +(correctionQueueEntry: CorrectionQueueEntry): Merger = {
      Merger(cumulator, set + correctionQueueEntry)
    }

    def -(correctionQueueEntry: CorrectionQueueEntry): Merger = {
      Merger(cumulator, set - correctionQueueEntry)
    }

    def ++(newSet: Set[CorrectionQueueEntry]): Merger = {
      Merger(cumulator, set ++ newSet)
    }

    def apply(cumulator: CorrectionQueueEntry): Merger = {
      Merger(cumulator, set)
    }

    def fold(correctionEntries: Set[CorrectionQueueEntry], previousObject: PhysicsObject, correction: Correction): Merger = {
      correctionEntries.foldLeft(this) {
        (merger, entry) =>
          if (entry.physicsObject == previousObject) {
            val newObject = entry.correction.correct(previousObject).setMotion(entry.correction.afterContactPath)
            merger(CorrectionQueueEntry(newObject, correction))
          } else {
            merger + entry
          }
      }
    }
  }

}
