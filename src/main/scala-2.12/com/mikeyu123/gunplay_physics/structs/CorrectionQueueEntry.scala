package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class CorrectionQueueEntry(physicsObject: PhysicsObject, correction: Correction) {

  def toTuple: Tuple2[PhysicsObject, Correction] = {
    (physicsObject, correction)
  }
}
