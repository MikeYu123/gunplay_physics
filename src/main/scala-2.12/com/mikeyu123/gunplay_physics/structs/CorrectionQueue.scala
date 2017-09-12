package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

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

}
