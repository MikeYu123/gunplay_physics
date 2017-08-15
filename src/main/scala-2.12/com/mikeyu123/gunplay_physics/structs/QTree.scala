package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class QTree(objs: Set[PhysicsObject], nodes: Set[QTree], aabb: AABB, capacity: Int, depth: Int) {

//  TODO possibly refactor via inheritance & matches
  def insert(obj: PhysicsObject): QTree = {
    if (obj.getAabb.intersects(aabb)) {
      if (nodes.nonEmpty)
        QTree(objs, nodes.map(_.insert(obj)), aabb, capacity, depth)
      else if (objs.size < capacity || depth == 0)
        QTree(objs + obj, nodes, aabb, capacity, depth)
      else
        subdivide.insert(obj)
    } else this
  }

  def subdivide: QTree = {
    val nodeAabbs = aabb.divide
    val nodes: Set[QTree] = nodeAabbs.map(aabb => QTree(Set(), Set(), aabb, capacity, depth - 1))
    objs.foldLeft(QTree(Set(), nodes, aabb, capacity, depth)) { (tree, obj) => tree.insert(obj) }
  }
}
