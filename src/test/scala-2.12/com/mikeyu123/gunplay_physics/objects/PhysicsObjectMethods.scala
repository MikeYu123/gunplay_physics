package com.mikeyu123.gunplay_physics.objects

import java.util.UUID

import com.mikeyu123.gunplay_physics.{GraphicsSpec, structs}
import com.mikeyu123.gunplay_physics.structs._
import org.scalatest.Matchers._

class PhysicsObjectMethods extends GraphicsSpec {

  val rect = Rectangle(Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0))
  val mObj = MovableObject(rect, Point(0.5, 0.5), PhysicsProperties(Motion(Vector(0, 0), 0)))

  it should "toString movable" in {
    mObj.toString should equal(rect.debugToString)
  }

  it should "move movable" in {
    mObj.move(Vector(1, 0)).shape should equal(Rectangle(Point(1.5, 0.5), 1, 1))
  }


  it should "rotate movable" in {
    val sqr = Math.sqrt(2) / 2
    mObj.rotate(math.Pi / 4).shape should equal {
      rect.rotate(math.Pi / 4, mObj.center)
    }
  }

  it should "applyMoiton movable" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    mObj.applyMotion(motion).shape should equal {
      Rectangle(Point(2.0, -5.551115123125783E-17), Point(1.0, 0.0), Point(1.0, 1.0), Point(2.0, 1.0))
    }
  }

  it should "setMoiton movable" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    mObj.setMotion(motion).applyMotion.shape should equal {
      Rectangle(Point(2.0, -5.551115123125783E-17), Point(1.0, 0.0), Point(1.0, 1.0), Point(2.0, 1.0))
    }
  }

  it should "setMoiton movable 0" in {
    val path = Vector(1, 0)
    mObj.setMotion(path).applyMotion.shape should equal {
      Rectangle(Point(1, 0), Point(1, 1), Point(2, 1), Point(2, 0))
    }
  }

  val imObj = ImmovableObject(rect, Point(0.5, 0.5), PhysicsProperties(Motion(Vector(0, 0), 0)))

  it should "move immovable" in {
    imObj.move(Vector(1, 0)).shape should equal(Rectangle(Point(1.5, 0.5), 1, 1))
  }

  it should "rotate immovable" in {
    val sqr = Math.sqrt(2) / 2
    imObj.rotate(math.Pi / 4).shape should equal {
      rect.rotate(math.Pi / 4, mObj.center)
    }
  }

  it should "applyMoiton immovable" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    imObj.applyMotion(motion).shape should equal {
      Rectangle(Point(2.0, -5.551115123125783E-17), Point(1.0, 0.0), Point(1.0, 1.0), Point(2.0, 1.0))
    }
  }

  it should "setMoiton immovable" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    imObj.setMotion(motion).applyMotion.shape should equal {
      Rectangle(Point(2.0, -5.551115123125783E-17), Point(1.0, 0.0), Point(1.0, 1.0), Point(2.0, 1.0))
    }
  }

  it should "setMoiton immovable 0" in {
    val path = Vector(1, 0)
    imObj.setMotion(path).applyMotion.shape should equal {
      Rectangle(Point(1, 0), Point(1, 1), Point(2, 1), Point(2, 0))
    }
  }

  val stObj = StaticObject(rect, Point(0.5, 0.5))

  it should "move static" in {
    stObj.move(Vector(1, 0)) should equal(stObj)
  }

  it should "rotate static" in {
    val sqr = Math.sqrt(2) / 2
    stObj.rotate(math.Pi / 4) should equal {
      stObj
    }
  }

  it should "applyMoiton static" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    stObj.applyMotion(motion) should equal {
      stObj
    }
  }

  it should "setMoiton static" in {
    val motion = Motion(Vector(1, 0), math.Pi / 2)
    stObj.setMotion(motion).applyMotion should equal {
      stObj
    }
  }

  it should "setMoiton static 0" in {
    val path = Vector(1, 0)
    stObj.setMotion(path).applyMotion should equal {
      stObj
    }
  }
}
