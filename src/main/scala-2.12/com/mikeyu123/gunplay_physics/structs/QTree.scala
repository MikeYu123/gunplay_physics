package com.mikeyu123.gunplay_physics.structs

class QTree(primitives: Set[GeometryPrimitive], aabb: AABB, capacity: Int) {

  val nodesAabb: Set[AABB] = aabb.divide

  //  val node_1: QTree =
  //  val node_2: QTree =
  //  val node_3: QTree =
  //  val node_4: QTree =

  //  def sort_primitives: Map[AABB, Set[GeometryPrimitive]] = {
  //  def sort_primitives: Any = {
  //    //    primitives11
  //    val s: Set[GeometryPrimitive] = Set(Rectangle(Point(0, 0), Point(8, 8), Point(0, 0), Point(8, 8)))
  //    Set(s)
  //
  //    val base: Map[AABB, Set[GeometryPrimitive]] = Map[AABB, Set[GeometryPrimitive]]()
  //    val sets =
  //      primitives.fold(base) { case (sets1: Map[AABB, Set[GeometryPrimitive]], p: GeometryPrimitive) =>
  //        val pAabb: AABB = p.getAabb
  //        nodes_AABB.reduce { (sets2: Map[AABB, Set[GeometryPrimitive]], aabb: AABB) =>
  //          val pr = aabb.intersects(pAabb)
  //          if (pr) {
  //            val v = sets2.get(aabb)
  //            v match {
  //              case Some(set) => {
  //                sets2.updated(aabb, v.get + p)
  //              }
  //              case None => {
  //                sets2.updated(aabb, Set(p))
  //              }
  //            }
  //          }
  //          sets2
  //        }
  //        sets1
  //      }
  //    sets
  //  }

  def insert(sets: Map[AABB, Set[GeometryPrimitive]], p: GeometryPrimitive): Map[AABB, Set[GeometryPrimitive]] = {
    val pAabb: AABB = p.getAabb
    val w = nodesAabb.fold(sets) { case (set: Map[AABB, Set[GeometryPrimitive]], aabb: AABB) =>
      val pr = aabb.intersects(pAabb)
      if (pr) {
        val v: Set[GeometryPrimitive] = sets.getOrElse(aabb, Set[GeometryPrimitive]()) + p
        set.updated(aabb, v)
      } else set
    }
      Map[AABB, Set[GeometryPrimitive]]()
  }
}
