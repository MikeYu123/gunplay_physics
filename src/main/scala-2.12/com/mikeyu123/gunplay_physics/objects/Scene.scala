package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs._
import com.mikeyu123.gunplay_physics.util.ContactHandler


//object Scene {
//  def apply(objects: Set[PhysicsObject], properties: SceneProperties = SceneProperties(),
//            contactListener: ContactListener): Scene = {
//    val aabb: AABB = objects.foldLeft(AABB(0, 0, 0, 0)) {
//      (aabb, obj) =>
//        aabb + obj.aabb
//    }
//    val qTree = QTreeBuilder(objects, aabb, properties.capacity, properties.depth)
//    Scene(objects, properties, contactListener, qTree)
//  }
//}

case class Scene(objects: Set[PhysicsObject], properties: SceneProperties = SceneProperties(),
                 contactListener: ContactListener, qTree: QTree = QTree.default) {

  def +(physicsObject: PhysicsObject): Scene = {
    Scene(objects + physicsObject, properties, contactListener, qTree)
  }

  def -(physicsObject: PhysicsObject): Scene = {
    Scene(objects - physicsObject, properties, contactListener, qTree)
  }

  def -(uUID: UUID): Scene = {
    getObject(uUID).fold(this)(this - _)
  }

  def hasId(uUID: UUID): Boolean = {
    objects.exists(_.id == uUID)
  }

  def getObject(uUID: UUID): Option[PhysicsObject] = {
    val physicsObject = objects.find(_.id == uUID)
    physicsObject
  }

  def setObjects(newObjects: Set[PhysicsObject]): Scene = {
    Scene(newObjects, properties, contactListener, qTree)
  }

  def step: Scene = {
    val aabb: AABB = objects.foldLeft(AABB(0, 0, 0, 0)) {
      (aabb, obj) =>
        aabb + obj.aabb
    }
    val handler = ContactHandler.handle(objects, aabb, properties.capacity, properties.depth, contactListener)
    setObjects(handler.objs).setQTree(handler.qTree)
  }

  def setQTree(qTree: QTree): Scene = {
    Scene(objects, properties, contactListener, qTree)
  }

  def buildTree: QTree = {
    buildTree(objects)
  }

  def buildTree(objects: Set[PhysicsObject]): QTree = {
    val aabb: AABB = objects.foldLeft(AABB(0, 0, 0, 0)) {
      (aabb, obj) =>
        aabb + obj.aabb
    }
    QTreeBuilder(objects, aabb, properties.capacity, properties.depth)
  }

  def objectsByAabb(aabb: AABB): Set[PhysicsObject] = {
    qTree.getByAabb(aabb)
  }
}
