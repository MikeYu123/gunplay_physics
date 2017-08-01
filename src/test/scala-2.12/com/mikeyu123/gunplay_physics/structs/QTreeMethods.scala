package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._


class QTreeMethods extends GraphicsSpec {
  def spawnRect(x: Double, y: Double): Rectangle = {
    val side = 1.0
    Rectangle(Point(x - side / 2, y - side / 2), Point(x - side / 2, y + side / 2), Point(x + side / 2, y + side / 2), Point(x + side / 2, y - side / 2))
  }

  val r0 = spawnRect(1.5, 6.5)
  val r1 = spawnRect(2.5, 2.5)
  val r2 = spawnRect(2.5, 5.5)
  val r3 = spawnRect(4.5, 4.5)
  val r4 = spawnRect(5.5, 5.5)
  val r5 = spawnRect(6.5, 5.5)
  val r6 = spawnRect(6.5, 7.5)

  def setup: QTree = {
    val s: Set[GeometryPrimitive] = Set(r0, r1, r2, r3, r4, r5, r6)
    val aabb: AABB = AABB(Point(1, 2), Point(7, 8))
    val capacity: Int = 3
    new QTree(s, aabb, capacity)
  }

  it should "insert test" in {
    val qTree: QTree = setup
    val sets: Map[AABB, Set[GeometryPrimitive]] = Map()
    val q: Map[AABB, Set[GeometryPrimitive]] = qTree.insert(sets, spawnRect(1.5, 6.5))
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

  it should "traverse test" in {
    val qTree: QTree = setup
    val res = qTree.traverse

//    println(res)

//    res should equal {    }
  }

}
