package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet


class ContactSolverMethods extends GraphicsSpec {

  val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
  val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
  val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
  val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
  val p0 = MovableObject(r0, r0.center, pr0)
  val p1 = MovableObject(r1, r1.center, pr1)
  val solver = new ContactSolver(Contact(p0, p1))

  it should "getCorrectionVector test 0" in {
    val vector = Vector(1.5, -1.5)
    val res = solver.getCorrectionVector(r0, r1, vector)
    res.vector should equal(Vector(-0.5, 0.5))
  }

  it should "getCorrectionVector test 1" in {
    val vector = Vector(1, 3)
    val res = solver.getCorrectionVector(r1, r0, vector)
    res.vector should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 2" in {
    val vector = Vector(1, 3)
    val res = solver.getCorrectionVector(r1, r0, vector)
    res.vector should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 3" in {
    val vector = Vector(1, 3)
    val p0 = Set(Point(2.5, 0.5))
    val res = solver.getCorrectionVector(r1, p0, vector)
    res.vector should equal(Vector(-0.5, -1.5))
  }

  val r2 = Rectangle(Point(0, 1), Point(0, 4), Point(3, 4), Point(3, 1))
  val r3 = Rectangle(Point(1, 2), Point(3, 4), Point(5, 2), Point(3, 0))

  it should "getCorrectionVector test 4" in {

    val vector = Vector(2, 0)
    val res = solver.getCorrectionVector(r2, r3, vector)
    res.vector should equal(Vector(-2, 0))
  }

  it should "getContactTime test 0" in {
    //+
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = MovableObject(r1, r1.center, pr1)

    val res: ContactSolver = ContactSolver(p0, p1).getContactTime
    res.contactTime should equal(0.2)
  }

  it should "getContactTime test 1" in {
    //+
    val pr0 = PhysicsProperties(Motion(Vector(2, 0), 0))
    val pr1 = PhysicsProperties(Motion(Vector(-1, 1), 0))
    val p0 = MovableObject(r2, r2.center, pr0)
    val p1 = MovableObject(r3, r3.center, pr1)
    val res: ContactSolver = ContactSolver(p0, p1).getContactTime
    res.contactTime should equal(2.0d / 3.0d)
  }

  val r4 = Rectangle(Point(0, 0), Point(0, 2), Point(2, 2), Point(2, 0))
  val r5 = Rectangle(Point(3, -2), Point(3, 4), Point(5, 4), Point(5, -2))

  it should "getContactTime test 2" in {

    val pr0 = PhysicsProperties(Motion(Vector(3, 0), 0))
    val pr1 = PhysicsProperties(Motion(Vector(0, 0), 0))
    val p0 = MovableObject(r4, r4.center, pr0).applyMotion
    val p1 = ImmovableObject(r5, r5.center, pr1)
    val res: ContactSolver = ContactSolver(p0, p1).getContactTime
    res.contactTime should equal(2.0d / 3.0d)
  }

  it should "getContactTime test 3" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = MovableObject(r1, r1.center, pr1)
    val res: ContactSolver = ContactSolver(p0, p1).getContactTime
    (res.contactTime, res.contact.normal) should equal((0.2, Vector(-1, -1).normalize))
  }

  it should "solve test 0" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = MovableObject(r1, r1.center, pr1)
    val con = Contact(p0, p1)
    val res = ContactSolver.solve(con)
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 1" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = ImmovableObject(r1, r1.center, pr1)
    val con = Contact(p0, p1)
    val res = ContactSolver.solve(con)
    val t = res.head.correction.contactTime
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 2" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = ImmovableObject(r1, r1.center, pr1)
    val con = Contact(p1, p0)
    val res = ContactSolver.solve(con)
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 2.1" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = ImmovableObject(r1, r1.center, pr1)
    val con = Contact(p1, p0)
    print("Object ")
    val res = time {
      ContactSolver.solve(con)
    }
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 2.2" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = ImmovableObject(r1, r1.center, pr1)
    val con = Contact(p1, p0)
    print("Case class ")
    val res = time {
      ContactSolver.solve(con)
    }
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 2.3" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = ImmovableObject(r1, r1.center, pr1)
    val con = Contact(p1, p0)
    val res = ContactSolver.solve(con)
    res.head.correction.contactTime should equal(0.2)
  }

  it should "solve test 4" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(Motion(Vector(1, 3), 0))
    val st0 = PhysicsObjectFactory.spawnPhOb(0)
    val st1 = PhysicsObjectFactory.spawnPhOb(2)
    val con = Contact(st0, st0)
    val res = ContactSolver.solve(con)
    res.size should equal(0)
  }

  it should "solve test 5" in {
    val pr0 = PhysicsProperties(Motion(Vector(1.5, -1.5), 0))
    val p0 = MovableObject(r0, r0.center, pr0)
    val p1 = StaticObject(r1, r1.center)
    val con = Contact(p1, p0)
    val res = ContactSolver.solve(con)
    res.head.correction.contactTime should equal(1 / 3.0)
  }

  it should "solve test 6" in {
    val p0 = StaticObject(r0, r0.center)
    val p1 = StaticObject(r1, r1.center)
    val con = Contact(p1, p0)
    val res = ContactSolver.solve(con)
    res.size should equal(0)
  }

  it should "solve test 7" in {
    val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0).applyMotion
    val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 1.5, -1, -1).applyMotion
    val con = Contact(obj0, obj2)
    val res = ContactSolver.solve(con)
    res.head.correction.contactTime should equal(1 / 2.0)
  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }
}
