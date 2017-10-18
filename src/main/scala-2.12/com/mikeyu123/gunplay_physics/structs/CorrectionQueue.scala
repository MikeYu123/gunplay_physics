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

  //  def getMultipleCorrections: Map[PhysicsObject, SortedSet[Correction]] = {
  //    map.filter(_._2.size > 1)
  //  }

//  def add(correctionQueueEntry: CorrectionQueueEntry): CorrectionQueue={
//    CorrectionQueue(map.updated(correctionQueueEntry.physicsObject, SortedSet(correctionQueueEntry.correction)))
//  }

  def getMultipleCorrections: Set[PhysicsObject] = {
    map.filter(_._2.size > 1).keySet
  }

  def applyCorrections: Set[PhysicsObject] = {
    //    map.map{pair=>
    //      pair._2._1.move()
    //    }
    Set()
  }

  def mergeCorrections: CorrectionQueue = {
    val mults = getMultipleCorrections
    mults.foreach {
      phOb =>

    }
    new CorrectionQueue(Map())
  }

  def mergeCorrections(physicsObject: PhysicsObject): CorrectionQueue = {
    val corrs = map(physicsObject)
    corrs.reduceLeft {
      (res: Correction, corr: Correction) =>
        val newOb = physicsObject.revert(res.contactTime).setMotion(res.afterContactPath)
        val other = corr.contact.other(physicsObject)
        val newContact = Contact(newOb, other)
        val correctionEntries = ContactSolver.solve(newContact)

//        correctionEntries.size match {
//          case 2 =>
//            val
//        }


    }

    this
  }
}
