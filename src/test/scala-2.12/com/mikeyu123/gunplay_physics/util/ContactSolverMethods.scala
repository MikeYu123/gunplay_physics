package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet
import com.mikeyu123.gunplay_physics.structs.ObjectType._


class ContactSolverMethods extends GraphicsSpec {


  it should "solve test 0" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val pr0 = PhysicsProperties(0, movable, Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(0, movable, Motion(Vector(1, 3), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    //    ContactHandler.handle(Set(obj0, obj1), AABB(-2, -2, 2, 2), 4, 4)

    val con = Contact(p0, p1)
    val res = ContactSolver.solve(con)
    res.head._2._3 should equal(0.2)
  }

  it should "getCorrectionVector test 0" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val vector = Vector(1.5, -1.5)
    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res._1 should equal(Vector(-0.5, 0.5))
  }

  it should "getCorrectionVector test 1" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val r1 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val vector = Vector(1, 3)

    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res._1 should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 2" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0)).move(-0.25, 0)
    val r1 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val vector = Vector(1, 3)

    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res._1 should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 3" in {
    val r0 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0)).move(-0.25, 0)
    val vector = Vector(1, 3)
    val p0 = Set(Point(2.5, 0.5))
    val res = ContactSolver.getCorrectionVector(r0, p0, vector)
    res._1 should equal(Vector(-0.5, -1.5))
  }

  it should "getCorrectionVector test 4" in {
    val r0 = Rectangle(Point(0, 1), Point(0, 4), Point(3, 4), Point(3, 1))
    val r1 = Rectangle(Point(1, 2), Point(3, 4), Point(5, 2), Point(3, 0))
    val vector = Vector(2, 0)
    val res = ContactSolver.getCorrectionVector(r0, r1, vector)
    res._1 should equal(Vector(-2, 0))
  }

  it should "getContactTime test 0" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val pr0 = PhysicsProperties(0, movable, Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(0, movable, Motion(Vector(1, 3), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res._1 should equal(0.2)
  }

  it should "getContactTime test 1" in {
    val r0 = Rectangle(Point(0, 1), Point(0, 4), Point(3, 4), Point(3, 1))
    val r1 = Rectangle(Point(1, 2), Point(3, 4), Point(5, 2), Point(3, 0))
    val pr0 = PhysicsProperties(0, movable, Motion(Vector(2, 0), 0))
    val pr1 = PhysicsProperties(0, movable, Motion(Vector(-1, 1), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res._1 should equal(2.0d / 3.0d)
  }


  it should "getContactTime test 2" in {
    val r0 = Rectangle(Point(0, 0), Point(0, 2), Point(2, 2), Point(2, 0))
    val r1 = Rectangle(Point(3, -2), Point(3, 4), Point(5, 4), Point(5, -2))
    val pr0 = PhysicsProperties(0, movable, Motion(Vector(3, 0), 0))
    val pr1 = PhysicsProperties(0, immovable, Motion(Vector(0, 0), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0).applyMotion
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res._1 should equal(2.0d / 3.0d)
  }

  it should "getContactTime test 3" in {
    val r0 = Rectangle(Point(0.5, 2.5), Point(2.5, 4.5), Point(4.5, 2.5), Point(2.5, 0.5))
    val r1 = Rectangle(Point(3, 0), Point(3, 2), Point(5, 2), Point(5, 0))
    val pr0 = PhysicsProperties(0, movable, Motion(Vector(1.5, -1.5), 0))
    val pr1 = PhysicsProperties(0, movable, Motion(Vector(1, 3), 0))
    val p0 = PhysicsObject(r0, r0.center, pr0)
    val p1 = PhysicsObject(r1, r1.center, pr1)
    val res = ContactSolver.getContactTime(p0, p1)
    res should equal((0.2, Vector(-1, -1).normalize))
  }
}
