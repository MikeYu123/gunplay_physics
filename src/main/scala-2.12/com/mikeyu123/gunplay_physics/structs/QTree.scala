package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

class QTree(primitives: Set[PhysicsObject], aabb: AABB, capacity: Int) {

  val nodesAabb: Set[AABB] = aabb.divide
//  Map that specifies 'belongs_to' relation between aabbs(Qtree nodes) and primitives inside it
  type AabbMap = Map[AABB, Set[PhysicsObject]]

  val children: Set[QTree] = if (primitives.size > capacity) sortPrimitives.foldLeft(Set[QTree]()) {
    (childs, m) =>
      childs + new QTree(m._2, m._1, capacity)
  } else Set[QTree]()

//  def traverse: Unit = {
//    if (childs.nonEmpty) {
////      println(this.aabb)
//      childs.map(_.traverse)
//    }
//    else println(this.aabb)
//  }

  def sortPrimitives: AabbMap = {
    val base: AabbMap = Map[AABB, Set[PhysicsObject]]()
    primitives.foldLeft(base)(insert)
  }

  def insert(sets: AabbMap, p: PhysicsObject): AabbMap = {
    val pAabb: AABB = p.getAabb
    nodesAabb.foldLeft(sets) { (set: AabbMap, aabb: AABB) =>
      val pr = aabb.intersects(pAabb)
      if (pr) {
        val newSet: Set[PhysicsObject] = sets.getOrElse(aabb, Set[PhysicsObject]()) + p
        set + (aabb -> newSet)
      } else set
    }
  }
}
