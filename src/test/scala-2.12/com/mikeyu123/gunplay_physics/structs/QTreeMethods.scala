package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import org.scalatest.Matchers._


class QTreeMethods extends GraphicsSpec {
  def spawnRect(x: Double, y: Double): Rectangle = {
    val side = 1.0
    Rectangle(Point(x - side / 2, y - side / 2), Point(x - side / 2, y + side / 2), Point(x + side / 2, y + side / 2), Point(x + side / 2, y - side / 2))
  }

  val pr = PhysicsProperties(0, true, false)
  val r0 = PhysicsObject(spawnRect(1.5, 6.5), Point(0, 0), pr)
  val r1 = PhysicsObject(spawnRect(2.5, 2.5), Point(0, 0), pr)
  val r2 = PhysicsObject(spawnRect(2.5, 5.5), Point(0, 0), pr)
  val r3 = PhysicsObject(spawnRect(4.5, 4.5), Point(0, 0), pr)
  val r4 = PhysicsObject(spawnRect(5.5, 5.5), Point(0, 0), pr)
  val r5 = PhysicsObject(spawnRect(6.5, 5.5), Point(0, 0), pr)
  val r6 = PhysicsObject(spawnRect(6.5, 7.5), Point(0, 0), pr)

  def setup: QTree = {
    val s: Set[PhysicsObject] = Set(r0, r1, r2, r3, r4, r5, r6)
    val aabb: AABB = AABB(Point(1, 2), Point(7, 8))
    val capacity: Int = 3
    new QTree(s, aabb, capacity)
  }

  it should "insert test" in {
    val qTree: QTree = setup
    val sets: Map[AABB, Set[PhysicsObject]] = Map()
    val q: Map[AABB, Set[PhysicsObject]] = qTree.insert(sets, r0)
    q should equal {
      Map((AABB(Point(1, 5), Point(4, 8)), Set(r0)))
    }
  }

  it should "sort test" in {
    val qTree: QTree = setup
    val res = qTree.sortPrimitives
    res should equal {
      Map((AABB(Point(4, 5), Point(7, 8)),
        Set(r3, r4, r5, r6)),
        (AABB(Point(1, 5), Point(4, 8)),
          Set(r0, r2, r3)),
        (AABB(Point(1, 2), Point(4, 5)),
          Set(r1, r2, r3)),
        (AABB(Point(4, 2), Point(7, 5)),
          Set(r3, r4, r5))
      )
    }
  }

  //  it should "traverse test" in {
  //    val qTree: QTree = setup
  //    val res = qTree.traverse

  //    println(res)

  //    res should equal {    }
  //  }

}
