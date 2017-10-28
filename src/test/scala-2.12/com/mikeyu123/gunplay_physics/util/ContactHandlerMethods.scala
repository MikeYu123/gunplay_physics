package com.mikeyu123.gunplay_physics.util

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.PhysicsObject
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

import scala.collection.immutable.HashSet


class ContactHandlerMethods extends GraphicsSpec {

  def c(n: Int, k: Int): Int = {
    val nk = (n - k + 1) to n
    val k1 = 1 to k
    val q = nk.product
    val w = k1.product
    q / w
  }

  def c(n: Int): Int = {
    c(n, 2)
  }

  it should "c test" in {
    c(7, 2) should equal {
      c(7)
    }
  }

  it should "combinations test 0" in {
    val set = for (i <- 1 to 7) yield PhysicsObjectFactory.spawnPhOb(i)
    val comb = ContactHandler.getCombinations(set.toSet)
    comb.size should equal {
      21
    }
  }

  it should "combinations test 1" in {
    val set = for (i <- 1 to 10) yield PhysicsObjectFactory.spawnPhOb(i)
    val comb = ContactHandler.getCombinations(set.toSet)
    comb.size should equal {
      c(10)
    }
  }

  it should "aabb contact test 0" in {
    val a = PhysicsObjectFactory.spawnPhOb(0, 0)
    val b = PhysicsObjectFactory.spawnPhOb(1, 1)
    val con: Option[Contact] = ContactHandler.aabbContact(a, b)
    con.getOrElse(0) should equal {
      Contact(a, b)
    }
  }

  it should "aabb contact test 1" in {
    val a = PhysicsObjectFactory.spawnPhOb(0, 0)
    val b = PhysicsObjectFactory.spawnPhOb(1, 2)
    val con: Option[Contact] = ContactHandler.aabbContact(a, b)
    con.getOrElse(0) should equal {
      0
    }
  }

  it should "combination contact test" in {
    val a = PhysicsObjectFactory.spawnPhOb(0, 0)
    val b = PhysicsObjectFactory.spawnPhOb(1, 0)
    val c = PhysicsObjectFactory.spawnPhOb(5, 0)
    val set = Set(a, b, c)
    val res = ContactHandler.getAabbContactsFromLeaf(set)
    res should equal {
      Set(Contact(a, b))
    }
  }

  def qtreeSetup: QTree = {
    val aabb: AABB = AABB(0, 0, 6, 6)
    val capacity: Int = 3
    val depth: Int = 4
    val objs = PhysicsObjectFactory.spawnPhOb((1, 1), (2, 1), (4, 3), (5, 3), (4, 5))
    QTreeBuilder(objs, aabb, capacity, depth)
  }

  it should "getAabbContacts" in {
    val tree: QTree = qtreeSetup
    val n = tree.nodes
    val res: HashSet[Contact] = ContactHandler.getAabbContacts(tree)
    val reqRes = HashSet(Contact(PhysicsObjectFactory.spawnPhOb(1, 1), PhysicsObjectFactory.spawnPhOb(2, 1)),
      Contact(PhysicsObjectFactory.spawnPhOb(5, 3), PhysicsObjectFactory.spawnPhOb(4, 3)))
    //    res should equal { reqRes }
  }

  it should "handle test" in {

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
    val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0)
    val obj1 = PhysicsObjectFactory.spawnPhOb(0, 1, 0, -1)
    val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 0, -1, 0)
    val objs = Set(obj0, obj1, obj2)
    val res = ContactHandler.handle(objs, AABB(-3, -3, 3, 3), 4, 4, contactListener)

    res.size should equal(2)
    //    print(0)
  }


}
