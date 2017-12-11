package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.{MovableObject, PhysicsObject}
import com.mikeyu123.gunplay_physics.util.{ContactHandler, ContactSolver}
import org.scalactic.Equality
import org.scalatest.Matchers._

class CorrectionQueueMethods extends GraphicsSpec {

  implicit val physicsObjectEquality: Equality[PhysicsObject] = physicsObjectEq

  val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0).applyMotion
  val obj1 = PhysicsObjectFactory.spawnPhOb(1.5, 0, 0, -1).applyMotion
  val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 1.5, -1, -1).applyMotion
  val contact0 = Contact(obj0, obj1)
  val contact1 = Contact(obj1, obj2)
  val contact2 = Contact(obj0, obj2)

  val set = Set(contact0, contact1, contact2)

  def move(physicsObject: PhysicsObject, point: Point): PhysicsObject = {
    physicsObject.move(point - physicsObject.center)
  }

  it should "render correction" in {
    val cons = ContactHandler.getGeometryContacts(set)
    val queue = ContactHandler.getCorrectionsQueue(cons)
    val queue1 = queue.mergeCorrections
    val result: Set[PhysicsObject] = queue1.applyCorrections


    val o0 = move(obj0, Point(0.5, 0))
    val o1 = move(obj1, Point(1.5, -1))
    val o2 = move(obj2, Point(0.5, 1))
    val target = Set(move(obj0, Point(0.5, 0)),
      move(obj1, Point(1.5, -1)),
      move(obj2, Point(0.5, 1)))
    result should equal {
      target
    }
  }

}
