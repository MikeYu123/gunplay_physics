package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.structs.{ContactListener, SceneProperties}
import com.mikeyu123.gunplay_physics.util.ContactHandler

case class Scene(objects: Set[PhysicsObject], properties: SceneProperties, contactListener: ContactListener) {

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
    val newObjects = ContactHandler.handle(objects, aabb, properties.capacity, properties.depth, contactListener)
    setObjects(newObjects)
  }
}
