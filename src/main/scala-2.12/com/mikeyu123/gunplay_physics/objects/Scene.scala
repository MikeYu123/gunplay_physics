package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{AABB, ContactListener, QTree, SceneProperties}
import com.mikeyu123.gunplay_physics.util.ContactHandler

case class Scene(objects: Set[PhysicsObject], properties: SceneProperties,
                 contactListener: ContactListener, qTree: QTree = QTree.default) {

  def addObject(physicsObject: PhysicsObject): Scene = {
    Scene(objects + physicsObject, properties, contactListener)
  }

  def remove(physicsObject: PhysicsObject): Scene = {
    Scene(objects - physicsObject, properties, contactListener)
  }

  def remove(uUID: UUID): Scene = {
    getObject(uUID) match {
      case p: Some[PhysicsObject] =>
        remove(p.get)
      case _ => this
    }
  }

  def hasId(uUID: UUID): Boolean = {
    val physicsObject = objects.find(_.id == uUID)
    physicsObject match {
      case p: Some[PhysicsObject] =>
        true
      case _ =>
        false
    }
  }

  def getObject(uUID: UUID): Option[PhysicsObject] = {
    val physicsObject = objects.find(_.id == uUID)
    physicsObject
  }

  def setObjects(newObjects: Set[PhysicsObject]): Scene = {
    Scene(newObjects, properties, contactListener)
  }

  def step: Scene = {
    val aabb = objects.map(_.getAabb).reduceLeft(_ + _)
    val handler = ContactHandler.handle(objects, aabb, properties.capacity, properties.depth, contactListener)
    setObjects(handler.objs).setQTree(handler.qTree)
  }

  def setQTree(qTree: QTree): Scene = {
    Scene(objects, properties, contactListener, qTree)
  }

  def getObjectsByAabb(aabb: AABB): Set[PhysicsObject] = {
    qTree.getByAabb(aabb)
    //    Set()
  }
}
