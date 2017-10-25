package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.util.{ContactHandler, ContactSolver}
import org.scalatest.Matchers._

class CorrectionQueueMethods extends GraphicsSpec {

  val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0)
  val obj1 = PhysicsObjectFactory.spawnPhOb(1, 0, 0, -1)
  val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 0, -1, 0)
  val contact0 = Contact(obj0, obj1)
  val contact1 = Contact(obj2, obj1)
  //  val contact2 = Contact(obj0, obj2)

  val set = Set(contact0, contact1)


  it should "render correction" in {

    //    ContactSolver.solve(contact0)

    //    val corrs = set.map(ContactSolver.solve)

    val queue = ContactHandler.getCorrectionsQueue(set)

//    val q = queue.mergeCorrections(obj1)
    val q0 = queue.mergeCorrections

//    val mult = queue.getMultipleCorrections

    print("0000")
  }

}
