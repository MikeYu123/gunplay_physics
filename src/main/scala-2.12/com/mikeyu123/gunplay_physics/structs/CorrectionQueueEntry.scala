package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class CorrectionQueueEntry(physicsObject: PhysicsObject, correction: Correction) {

  def toTuple: Tuple2[PhysicsObject, Correction] = {
    (physicsObject, correction)
  }

  def swapSubject(old: PhysicsObject, next: PhysicsObject): CorrectionQueueEntry = {
    physicsObject.equals(old) match {
      case true => CorrectionQueueEntry(next, correction.swapSubject(old, next))
      case _ => CorrectionQueueEntry(physicsObject, correction.swapSubject(old, next))
    }
  }
}
