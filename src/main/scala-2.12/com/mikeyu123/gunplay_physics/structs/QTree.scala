package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.util.ContactListner

class QTree(primitives: Set[GeometryPrimitive], aabb: AABB, capacity: Int) {

  val nodesAabb: Set[AABB] = aabb.divide
  type AabbMap = Map[AABB, Set[GeometryPrimitive]]

  val childs: Set[QTree] = if (primitives.size > capacity) sortPrimitives.foldLeft(Set[QTree]()) {
    (childs, m) =>
      childs + new QTree(m._2, m._1, capacity)
  } else Set[QTree]()

  def traverse: Unit = {
    if (childs.nonEmpty) {
//      println(this.aabb)
      childs.map(_.traverse)
    }
    else println(this.aabb)
  }

  def sortPrimitives: AabbMap = {
    val base: AabbMap = Map[AABB, Set[GeometryPrimitive]]()
    primitives.foldLeft(base)(insert)
  }

  def insert(sets: AabbMap, p: GeometryPrimitive): AabbMap = {
    val pAabb: AABB = p.getAabb
    nodesAabb.foldLeft(sets) { (set, aabb) =>
      val pr = aabb.intersects(pAabb)
      if (pr) {
        val newSet = sets.getOrElse(aabb, Set[GeometryPrimitive]()) + p
        set.updated(aabb, newSet)
      } else set
    }
  }
}
