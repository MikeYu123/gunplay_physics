package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.structs.{Contact, ContactListener, PhysicsObjectFactory, SceneProperties, Vector}
import org.scalatest.Matchers._

class SceneMethods extends GraphicsSpec {

  val contactListener = new ContactListener {
    override def preSolve(contact: Contact): Contact = {
      contact.ab.exists(_.properties.motion.path == Vector(0, -1)) && contact.ab.exists(_.properties.motion.path == Vector(1, 0))
      match {
        case true =>
          contact.a.properties.motion.path == Vector(0, -1) match {
            case true =>
              contact.removeA
            case _ => contact.removeB
          }
        case _ => contact
      }
    }

    override def postSolve(contact: Contact): Contact = contact
  }
  val obj0: PhysicsObject = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0)
  val obj1: PhysicsObject = PhysicsObjectFactory.spawnPhOb(0, 1, 0, -1)
  val obj2: PhysicsObject = PhysicsObjectFactory.spawnPhOb(1.5, 0, -1, 0)
  val obj3: PhysicsObject = PhysicsObjectFactory.spawnPhOb(1.5, 0, -1, 0)
  val objs = Set(obj0, obj1, obj2)
  val scene = Scene(objs, SceneProperties(30, 4, 4), contactListener)

  it should "add object" in {

    scene.addObject(obj3).objects should equal(objs + obj3)
  }

  it should "remove object by id" in {
    val id = obj1.id
    scene.remove(id).objects should equal(objs - obj1)
  }

  it should "remove object 0" in {
    scene.remove(obj1).objects should equal(objs - obj1)
  }

  it should "remove object 1" in {
    scene.remove(obj3) should equal(scene)
  }

  it should "remove object by incorrect id" in {
    val id = UUID.randomUUID()
    val newScene = scene.remove(id)
    newScene should equal(scene)
  }

  it should "get object" in {
    val op = scene.getObject(obj0.id)
    op.getOrElse(0) should equal(obj0)
  }

  it should "get object 1" in {
    val op = scene.getObject(UUID.randomUUID())
    op.getOrElse(0) should equal(0)
  }

  it should "has object 0" in {
    scene.hasId(obj1.id) should equal(true)
  }

  it should "has object 1" in {
    scene.hasId(UUID.randomUUID()) should equal(false)
  }

  it should "set objects 0" in {
    val newObjects = objs + obj3
    scene.setObjects(newObjects) should equal(Scene(newObjects, scene.properties, scene.contactListener))
  }

  it should "step 0" in {
    val newScene = scene.step
    newScene.objects should equal(Set(obj0.move(Vector(0.25, 0)), obj2.move(Vector(-0.25, 0))))
  }

  it should "create scene" in {
    val scene0 = Scene(objs, SceneProperties(30, 4, 4), contactListener)
    scene0.objects.size should equal (3)
  }
}
