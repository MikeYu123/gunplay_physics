package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

object PhysicsObjectFactory {

  def spawnRect(x: Double, y: Double): Rectangle = {
    spawnRect(x, y, 1)
  }

  def spawnRect(x: Double, y: Double, side: Double): Rectangle = {
    Rectangle(Point(x - side / 2, y - side / 2), Point(x - side / 2, y + side / 2),
      Point(x + side / 2, y + side / 2), Point(x + side / 2, y - side / 2))
  }

  def spawnPhOb(x: Double, y: Double): PhysicsObject = {
    PhysicsObject(spawnRect(x, y), Point(0, 0), pr)
  }

  val pr = PhysicsProperties(0, true, false, Set(), Motion(Vector(0, 0), 0))

  def spawnPhOb(d: Double): PhysicsObject = {
    val pr = PhysicsProperties(0, true, false, Set(), Motion(Vector(0, 0), 0))
    PhysicsObject(GeometryStub(d), Point(d, d), pr)
  }

  def spawnPhOb(coords: Tuple2[Double, Double]*): Set[PhysicsObject] = {
    coords.map {
      t =>
        spawnPhOb(t._1, t._2)
    }.toSet
  }
}
