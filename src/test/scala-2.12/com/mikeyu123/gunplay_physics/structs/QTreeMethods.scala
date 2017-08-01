package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import org.scalatest.Matchers._


class QTreeMethods extends GraphicsSpec {
  def spawnRect(x: Double, y: Double): Rectangle = {
    val side = 1.0
    Rectangle(Point(x - side / 2, y - side / 2), Point(x - side / 2, y + side / 2), Point(x + side / 2, y + side / 2), Point(x + side / 2, y - side / 2))
  }

  it should "insert test" in {
    val s: Set[GeometryPrimitive] = Set(spawnRect(1.5, 6.5), spawnRect(2.5, 2.5), spawnRect(2.5, 5.5),
      spawnRect(4.5, 4.5), spawnRect(5.5, 5.5), spawnRect(6.5, 5.5), spawnRect(6.5, 7.5))
    val aabb: AABB = AABB(Point(1, 2), Point(7, 8))
    val capacity: Int = 3
    val qTree: QTree = new QTree(s, aabb, capacity)
    val sets: Map[AABB, Set[GeometryPrimitive]] = Map()
    val q: Map[AABB, Set[GeometryPrimitive]] = qTree.insert(sets, spawnRect(1.5, 6.5))
    q should equal {
          Map((AABB(Point(1,5), Point(4,8)), Set(spawnRect(1.5, 6.5))))
    }
  }

}
