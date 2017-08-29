package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet


class ContactSolverMethods extends GraphicsSpec {


  it should "solve test" in {
    val obj0 = PhysicsObjectFactory.spawnPhOb(-1.5, 0, 1, 0)
    val obj1 = PhysicsObjectFactory.spawnPhOb(0, -1, 0, 0.5)
    //    ContactHandler.handle(Set(obj0, obj1), AABB(-2, -2, 2, 2), 4, 4)

    val con = Contact(obj0.applyMotion, obj1.applyMotion, 0)
    ContactSolver.solve(con)
  }

  it should "getCorrectionVector test 0" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val vector = Vector(1.5, -1.5)
    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res should equal(Vector(-0.5, 0.5))
  }

  it should "getCorrectionVector test 1" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val r1 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val vector = Vector(1, 3)

    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 2" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0)).move(-0.25, 0)
    val r1 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val vector = Vector(1, 3)

    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 3" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0)).move(-0.25, 0)
    val vector = Vector(1, 3)
    val p0 = Set(Point(2.5, 0.5))
    val res = ContactSolver.getCorrectionVector(r0, p0, vector)
    res should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 4" in {
    val r0 = Rectangle(Point(0, 1), Point(0, 4), Point(3, 4), Point(3, 1))
    val r1 = Rectangle(Point(1, 2), Point(3, 4), Point(5, 2), Point(3, 0))
    val vector = Vector(2, 0)
    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res should equal(Vector(-2, 0))
  }

  it should "getContactTime test 0" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val pr0 = PhysicsProperties(0, movable = true, locked = false, Set(), Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(0, movable = true, locked = false, Set(), Motion(Vector(1, 3), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res should equal(0.2)
  }

  it should "getContactTime test 1" in {
    val r0 = Rectangle(Point(0, 1), Point(0, 4), Point(3, 4), Point(3, 1))
    val r1 = Rectangle(Point(1, 2), Point(3, 4), Point(5, 2), Point(3, 0))
    val pr0 = PhysicsProperties(0, movable = true, locked = false, Set(), Motion(Vector(2, 0), 0))
    val pr1 = PhysicsProperties(0, movable = true, locked = false, Set(), Motion(Vector(-1, 1), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res should equal(2.0d / 3.0d)
  }
}
