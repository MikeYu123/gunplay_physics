package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import org.scalatest.Matchers._


class QTreeMethods extends GraphicsSpec {
  def spawnRect(x: Double, y: Double): Rectangle = {
    val side = 1.0
    Rectangle(Point(x - side / 2, y - side / 2), Point(x - side / 2, y + side / 2), Point(x + side / 2, y + side / 2), Point(x + side / 2, y - side / 2))
  }

  def spawnPhOb(x: Double, y: Double): PhysicsObject = {
    PhysicsObject(spawnRect(x, y), Point(0, 0), pr)
  }

  def aabb(x0: Double, y0: Double, x1: Double, y1: Double): AABB = {
    AABB(Point(x0, y0), Point(x1, y1))
  }

  val pr = PhysicsProperties(0, true, false, Set(), Motion(Vector(0, 0), 0))
  val r0 = spawnPhOb(1.5, 6.5)
  val r1 = spawnPhOb(2.5, 2.5)
  val r2 = spawnPhOb(2.5, 5.5)
  val r3 = spawnPhOb(4.5, 4.5)
  val r4 = spawnPhOb(5.5, 5.5)
  val r5 = spawnPhOb(6.5, 5.5)
  val r6 = spawnPhOb(6.5, 7.5)

  def setup: QTree = {
    val s: Set[PhysicsObject] = Set(r0, r1, r2, r3, r4, r5, r6)
    val aabb: AABB = AABB(Point(1, 2), Point(7, 8))
    val capacity: Int = 3
    val depth: Int = 4
    QTreeBuilder(s, aabb, capacity, depth)
  }

  it should "insert test" in {
    val qTree: QTree = setup

    val q0 = QTree(Set(r2, r3, r1), Set(), aabb(1, 2, 4, 5), 3, 3)
    val q1 = QTree(Set(r0, r2, r3), Set(), aabb(1, 5, 4, 8), 3, 3)
    val q2 = QTree(Set(r5, r3, r4), Set(), aabb(4, 2, 7, 5), 3, 3)

    val q30 = QTree(Set(r3, r4), Set(), aabb(4, 5, 5.5, 6.5), 3, 2)
    val q31 = QTree(Set(), Set(), aabb(4, 6.5, 5.5, 8), 3, 2)
    val q32 = QTree(Set(r6), Set(), aabb(5.5, 6.5, 7, 8), 3, 2)
    val q33 = QTree(Set(r5, r4), Set(), aabb(5.5, 5, 7, 6.5), 3, 2)

    val q3 = QTree(Set(), Set(q32, q31, q30, q33), aabb(4, 5, 7, 8), 3, 3)

    qTree should equal {
      QTree(Set(), Set(q3, q1, q0, q2), aabb(1, 2, 7, 8), 3, 4)
    }
  }

}
