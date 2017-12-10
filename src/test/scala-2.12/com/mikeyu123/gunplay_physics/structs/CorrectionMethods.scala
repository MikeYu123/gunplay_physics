package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.{MovableObject, PhysicsObject}
import com.mikeyu123.gunplay_physics.util.ContactSolver
import org.scalatest.Matchers._

class CorrectionMethods extends GraphicsSpec {

  implicit val physicsObjectEquality = physicsObjectEq
  //  implicit val movableObjectEquality = movableObjectEq

  val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
  val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
  val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
  val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
  val p0 = MovableObject(r0, r0.center, pr0)
  val p1 = MovableObject(r1, r1.center, pr1)
  val cs: ContactSolver = ContactSolver(p0, p1).getContactTime


  it should "correct test 0" in {
    val corr = cs.correction(p0)
    corr.correct(p0) should equal {
      p0.move(p0.properties.motion.path * -0.2)
    }
  }

  it should "correct test 1" in {
    val corr = cs.correction(p1)
    corr.correct(p1) should equal {
      p1.move(p1.properties.motion.path * -0.2 + Vector(0.4, 0.4))
    }
  }

  it should "related test 0" in {
    val corr = cs.correction(p0)
    val corr0 = cs.correction(p1)
    corr.related(corr0) should equal {
      true
    }
  }

  it should "swap test 0" in {
    val corr = cs.correction(p0)
    val p2 = MovableObject(r0, r0.center, pr0)
    corr.swapSubject(p0, p2) should equal {
      Correction(Contact(Set[PhysicsObject](p1, p2), corr.contact.normal), corr.afterContactPath, corr.contactTime)
    }
  }
}
