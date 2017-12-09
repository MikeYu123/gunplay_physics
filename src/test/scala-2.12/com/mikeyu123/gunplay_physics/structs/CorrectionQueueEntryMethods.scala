package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.{MovableObject, PhysicsObject}
import com.mikeyu123.gunplay_physics.util.{ContactHandler, ContactSolver}
import org.scalatest.Matchers._

class CorrectionQueueEntryMethods extends GraphicsSpec {

 implicit val physicsObjectEquality = physicsObjectEq

  val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
  val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
  val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
  val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
  val p0 = MovableObject(r0, r0.center, pr0)
  val p1 = MovableObject(r1, r1.center, pr1)
  val cs: ContactSolver = ContactSolver(p0, p1).getContactTime

  it should "swap test 0" in {
    val entry = cs.entry(p0)
    val p2 = MovableObject(r0, r0.center, pr0)
    entry.swapSubject(p0, p2) should equal {
      CorrectionQueueEntry(p2,Correction(Contact(Set[PhysicsObject](p1, p2), entry.correction.contact.normal), entry.correction.afterContactPath, entry.correction.contactTime))
    }
  }

  it should "toTuple test 0" in{
    val entry = cs.entry(p0)
    entry.toTuple should equal{(p0, cs.correction(p0))}
  }

}
