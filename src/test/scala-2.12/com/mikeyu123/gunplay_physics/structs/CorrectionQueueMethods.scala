package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.objects.{MovableObject, PhysicsObject, StaticObject}
import com.mikeyu123.gunplay_physics.util.{ContactHandler, ContactSolver}
import org.scalactic.Equality
import org.scalatest.Matchers._

class CorrectionQueueMethods extends GraphicsSpec {

  implicit val physicsObjectEquality: Equality[PhysicsObject] = physicsObjectEq

  val obj0 = PhysicsObjectFactory.spawnPhOb(0, 0, 1, 0).applyMotion
  val obj1 = PhysicsObjectFactory.spawnPhOb(1.5, 0, 0, -1).applyMotion
  val obj2 = PhysicsObjectFactory.spawnPhOb(1.5, 1.5, -1, -1).applyMotion
  val contact0 = Contact(obj0, obj1)
  val contact1 = Contact(obj1, obj2)
  val contact2 = Contact(obj0, obj2)

  val set = Set(contact0, contact1, contact2)

  def move(physicsObject: PhysicsObject, point: Point): PhysicsObject = {
    physicsObject.move(point - physicsObject.center)
  }

  it should "merge corrections" in {
    val cons = ContactHandler.getGeometryContacts(set)
    val queue = ContactHandler.getCorrectionsQueue(cons)
    val queue1 = queue.mergeCorrections
    val result: Set[PhysicsObject] = queue1.applyCorrections

    val target = Set(move(obj0, Point(0.5, 0)),
      move(obj1, Point(1.5, -1)),
      move(obj2, Point(0.5, 1)))
//    result should equal {
//      target
//    }
  }


//  it should "merge corrections 0" in {
//    val _obj0 = StaticObject(Rectangle(Point(3, 0), 6, 2), Point(3, 0))
//    val _obj1 = MovableObject(Rectangle(Point(0.5, 2.5), 1, 1), Point(0.5, 2.5),
//      PhysicsProperties(Motion(Vector(1, -1), math.Pi / 4d))).applyMotion
//    val _obj2 = PhysicsObjectFactory.spawnPhOb(2.5, 2.5, 0, -1.5).applyMotion
//    val _obj3 = PhysicsObjectFactory.spawnPhOb(4.5, 1.5, -1.5, 0).applyMotion
//
//    val objs = Set(_obj0, _obj1, _obj2, _obj3)
//    val aabb = ContactHandler.getAabbContactsFromLeaf(objs)
//    val cons = ContactHandler.getGeometryContacts(aabb)
//    val queue = ContactHandler.getCorrectionsQueue(cons)
//    val queue1 = queue.mergeCorrections
//    val result: Set[PhysicsObject] = queue1.applyCorrections
////    print(0)
//    // (1.5, 1.5) (2.5, 1.0)
//    //    result should equal {
//    //    }
//  }

  it should "merge corrections 2" in {
    val _obj1 = MovableObject(Rectangle(Point(0.5, 2.5), 1, 1), Point(0.5, 2.5),
      PhysicsProperties(Motion(Vector(1, -1), math.Pi / 4d))).applyMotion
    val _obj2 = PhysicsObjectFactory.spawnPhOb(2.5, 2.5, 0, -1.5).applyMotion

    val c = Contact(_obj1, _obj2)

    val cs = ContactSolver(c)

    val q = math.sqrt(2)/2 - 0.5
    val res = cs.getContactTime(CorrectionVector(Vector(q, q/2), Vector(0, 1)))

    print(res)
  }
}
