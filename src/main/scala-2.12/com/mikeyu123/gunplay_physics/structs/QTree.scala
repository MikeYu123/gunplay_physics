package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject
//import scala.collection.{Set, Iterable}

object QTree {
  val default = QTree(Set(), Set(), AABB(0, 0, 0, 0), 0, 0)
}

case class QTree(objects: Set[PhysicsObject], nodes: Set[QTree], aabb: AABB, capacity: Int, depth: Int) extends Iterable[Set[PhysicsObject]] {

  //  TODO possibly refactor via inheritance & matches
  def insert(obj: PhysicsObject): QTree = {
    if (obj.aabb.intersects(aabb)) {
      if (nodes.nonEmpty)
        QTree(objects, nodes.map(_.insert(obj)), aabb, capacity, depth)
      else if (objects.size < capacity || depth == 0)
        QTree(objects + obj, nodes, aabb, capacity, depth)
      else
        subdivide.insert(obj)
    } else this
  }

  def subdivide: QTree = {
    val nodeAabbs = aabb.divide
    val nodes: Set[QTree] = nodeAabbs.map(aabb => QTree(Set(), Set(), aabb, capacity, depth - 1))
    objects.foldLeft(QTree(Set(), nodes, aabb, capacity, depth))((tree, obj) => tree.insert(obj))
  }

  def traverse: Set[Set[PhysicsObject]] = {
    this.traverseNodes
  }

  def traverseNodes: Set[Set[PhysicsObject]] = {
    if (this.objects.nonEmpty)
      Set(this.objects)
    else if (this.nodes.nonEmpty)
      this.nodes.foldLeft(Set[Set[PhysicsObject]]()) { (set, node) => set ++ node.traverseNodes }
    else Set()
  }

  def getByAabb(aABB: AABB): Set[PhysicsObject] = {
    val objs: Set[PhysicsObject] = objects.collect{
      case o if o.aabb.intersects(aABB) => o
    }
    val nodesObjs: Set[Set[PhysicsObject]] = nodes.collect {
      case n if n.aabb.intersects(aABB) => n.getByAabb(aABB)
    }
    nodesObjs.foldLeft(objs)(_++_)
  }

  override def iterator: Iterator[Set[PhysicsObject]] = {
    this.traverse.iterator
  }
}
