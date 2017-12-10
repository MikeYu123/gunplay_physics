package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.util.{ContactHandler, ContactSolver}
import org.scalatest.Matchers._

class CorrectionQueueMethods extends GraphicsSpec {

  val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0).applyMotion
  val obj1 = PhysicsObjectFactory.spawnPhOb(1.5, 0, 0, -1).applyMotion
  val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 1.5, -1, -1).applyMotion
  val contact0 = Contact(obj0, obj1)
  val contact1 = Contact(obj1, obj2)
  val contact2 = Contact(obj0, obj2)

  val set = Set(contact0, contact1, contact2)


  it should "render correction" in {

    val queue = ContactHandler.getCorrectionsQueue(ContactHandler.getGeometryContacts(set))
    val q0 = queue.mergeCorrections

    val res = q0.applyCorrections
    print("0000")
  }

}
