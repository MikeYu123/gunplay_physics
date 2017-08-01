package com.mikeyu123.gunplay_physics.structs

class QTree(primitives: Set[GeometryPrimitive], aabb: AABB, capacity: Int) {

  val nodesAabb: Set[AABB] = aabb.divide
  type AabbMap = Map[AABB, Set[GeometryPrimitive]]

  //  val node_1: QTree =
  //  val node_2: QTree =
  //  val node_3: QTree =
  //  val node_4: QTree =

  def sortPrimitives: AabbMap = {
    val base: AabbMap = Map[AABB, Set[GeometryPrimitive]]()
    primitives.foldLeft(base)(insert)
  }

  def insert(sets: AabbMap, p: GeometryPrimitive): AabbMap = {
    val pAabb: AABB = p.getAabb
    nodesAabb.foldLeft(sets) { case (set: AabbMap, aabb: AABB) =>
      val pr = aabb.intersects(pAabb)
      if (pr) {
        val newSet: Set[GeometryPrimitive] = sets.getOrElse(aabb, Set[GeometryPrimitive]()) + p
        set.updated(aabb, newSet)
      } else set
    }
  }
}
