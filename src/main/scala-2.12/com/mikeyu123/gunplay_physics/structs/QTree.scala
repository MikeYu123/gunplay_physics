package com.mikeyu123.gunplay_physics.structs

class QTree(primitives: Set[GeometryPrimitive], aabb: AABB, capacity: Int) {

  val nodesAabb: Set[AABB] = aabb.divide

  //  val node_1: QTree =
  //  val node_2: QTree =
  //  val node_3: QTree =
  //  val node_4: QTree =

//    def sort_primitives: Map[AABB, Set[GeometryPrimitive]] = {
//      //    primitives11
//      val s: Set[GeometryPrimitive] = Set(Rectangle(Point(0, 0), Point(8, 8), Point(0, 0), Point(8, 8)))
//      Set(s)
//
//      val base: Map[AABB, Set[GeometryPrimitive]] = Map[AABB, Set[GeometryPrimitive]]()
//      val sets: Map[AABB, Set[GeometryPrimitive]]  =
//        primitives.fold(base){insert}
//    }

  def insert(sets: Map[AABB, Set[GeometryPrimitive]], p: GeometryPrimitive): Map[AABB, Set[GeometryPrimitive]] = {
    val pAabb: AABB = p.getAabb
    nodesAabb.fold(sets) { case (set: Map[AABB, Set[GeometryPrimitive]], aabb: AABB) =>
      val pr = aabb.intersects(pAabb)
      if (pr) {
        val newSet: Set[GeometryPrimitive] = sets.getOrElse(aabb, Set[GeometryPrimitive]()) + p
        set.updated(aabb, newSet)
      } else set
    }.asInstanceOf[Map[AABB, Set[GeometryPrimitive]]]
  }
}
