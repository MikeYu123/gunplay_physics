package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.{ImmovableObject, MovableObject, PhysicsObject, StaticObject}

object PhysicsObjectFactory {

  def spawnRect(x: Double, y: Double): Rectangle = {
    spawnRect(x, y, 1)
  }

  def spawnRect(x: Double, y: Double, side: Double): Rectangle = {
    Rectangle(Point(x - side / 2, y - side / 2),
      Point(x - side / 2, y + side / 2),
      Point(x + side / 2, y + side / 2),
      Point(x + side / 2, y - side / 2))
  }

  def spawnPhOb(x: Double, y: Double): PhysicsObject = {
    MovableObject(spawnRect(x, y), Point(0, 0), pr)
  }

  def spawnPhOb(x: Double, y: Double, vx: Double, vy: Double): PhysicsObject = {
    val pr = PhysicsProperties(0, Motion(Vector(vx, vy), 0))
    MovableObject(spawnRect(x, y), Point(x, y), pr)
  }

  val pr = PhysicsProperties(0, Motion(Vector(0, 0), 0))

  def spawnPhOb(d: Double): PhysicsObject = {
    MovableObject(GeometryStub(d), Point(d, d), pr)
  }

  def spawnImOb(d: Double): PhysicsObject = {
    ImmovableObject(GeometryStub(d), Point(d, d), pr)
  }

  def spawnStOb(d: Double): PhysicsObject = {
    StaticObject(GeometryStub(d), Point(d, d), pr)
  }

  def spawnPhOb(coords: Tuple2[Double, Double]*): Set[PhysicsObject] = {
    coords.map {
      t =>
        spawnPhOb(t._1, t._2)
    }.toSet
  }
}
