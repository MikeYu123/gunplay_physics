package com.mikeyu123.gunplay_physics.structs

class QTree(primitives: Set[GeometryPrimitive], aabb: AABB, capacity: Int) {

  val nodes_AABB: Set[AABB] = aabb.divide

//  val node_1: QTree =
//  val node_2: QTree =
//  val node_3: QTree =
//  val node_4: QTree =

  def sort_primitives: Set[Set[GeometryPrimitive]] = {
//    primitives11
    val s: Set[GeometryPrimitive] = Set(Rectangle(Point(0, 0), Point(8, 8),Point(0, 0), Point(8, 8)))
    Set(s)
  }
}
