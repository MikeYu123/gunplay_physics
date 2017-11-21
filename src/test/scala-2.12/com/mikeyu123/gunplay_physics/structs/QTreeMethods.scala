package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import org.scalatest.Matchers._


class QTreeMethods extends GraphicsSpec {

  val r0 = PhysicsObjectFactory.spawnPhOb(1.5, 6.5)
  val r1 = PhysicsObjectFactory.spawnPhOb(2.5, 2.5)
  val r2 = PhysicsObjectFactory.spawnPhOb(2.5, 5.5)
  val r3 = PhysicsObjectFactory.spawnPhOb(4.5, 4.5)
  val r4 = PhysicsObjectFactory.spawnPhOb(5.5, 5.5)
  val r5 = PhysicsObjectFactory.spawnPhOb(6.5, 5.5)
  val r6 = PhysicsObjectFactory.spawnPhOb(6.5, 7.5)
  val s: Set[PhysicsObject] = Set(r0, r1, r2, r3, r4, r5, r6)

  def setup: QTree = {

    val aabb: AABB = AABB(Point(1, 2), Point(7, 8))
    val capacity: Int = 3
    val depth: Int = 4
    QTreeBuilder(s, aabb, capacity, depth)
  }

  it should "insert test" in {
    val qTree: QTree = setup

    val q0 = QTree(Set(r2, r3, r1), Set(), AABB(1, 2, 4, 5), 3, 3)
    val q1 = QTree(Set(r0, r2, r3), Set(), AABB(1, 5, 4, 8), 3, 3)
    val q2 = QTree(Set(r5, r3, r4), Set(), AABB(4, 2, 7, 5), 3, 3)

    val q30 = QTree(Set(r3, r4), Set(), AABB(4, 5, 5.5, 6.5), 3, 2)
    val q31 = QTree(Set(), Set(), AABB(4, 6.5, 5.5, 8), 3, 2)
    val q32 = QTree(Set(r6), Set(), AABB(5.5, 6.5, 7, 8), 3, 2)
    val q33 = QTree(Set(r5, r4), Set(), AABB(5.5, 5, 7, 6.5), 3, 2)

    val q3 = QTree(Set(), Set(q32, q31, q30, q33), AABB(4, 5, 7, 8), 3, 3)

    qTree should equal {
      QTree(Set(), Set(q3, q1, q0, q2), AABB(1, 2, 7, 8), 3, 4)
    }
  }


  it should "traverse test" in {
    val qTree: QTree = setup
    val tr = qTree.traverse

    val target = Set(
      Set(r0, r2, r3),
      Set(r1, r2, r3),
      Set(r3, r4, r5),
      Set(r3, r4),
      Set(r4, r5),
      Set(r6)
    )
    val res = tr.equals(target)
    tr should equal {
      target
    }
  }


  it should "subset test" in {
    val qTree: QTree = setup
    val tr = qTree.traverse

    val q = tr.last
    val w = s.subsets(2)
  }
}
