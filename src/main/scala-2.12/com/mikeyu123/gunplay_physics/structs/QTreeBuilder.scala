package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

object QTreeBuilder {

  def apply(objs: Set[PhysicsObject], aabb: AABB, capacity: Int, depth: Int): QTree = {
    objs.foldLeft(QTree(Set(), Set(), aabb, capacity, depth)) { (tree, obj) => tree.insert(obj) }
  }
}
