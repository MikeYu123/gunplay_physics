package com.mikeyu123.gunplay_physics.util
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import com.mikeyu123.gunplay_physics.structs.{Point, Rectangle}

/**
  * Created by mihailurcenkov on 10.07.17.
  */
class CollisionDetectorSpec extends FlatSpec {
  it should "detect no collision on rects intersected by point" in {
    val rect1 = Rectangle(Point(0d, 0d), Point(0d, 1d), Point(1d, 1d), Point(1d, 0d))
    val rect2 = Rectangle(Point(1d, 1d), Point(1d, 4d), Point(4d, 4d), Point(4d, 1d))
    CollisionDetector.detectCollision(rect1, rect2) should equal(true)
  }

  it should "detect no collision on rects intersected by polygon" in {
    val rect1 = Rectangle(Point(0d, 0d), Point(0d, 2d), Point(2d, 2d), Point(2d, 0d))
    val rect2 = Rectangle(Point(1d, 1d), Point(1d, 3d), Point(3d, 3d), Point(3d, 1d))
    CollisionDetector.detectCollision(rect1, rect2) should equal(true)
  }

  it should "detect no collision on rects intersected by line" in {
    val rect1 = Rectangle(Point(0d, 0d), Point(0d, 2d), Point(2d, 2d), Point(2d, 0d))
    val rect2 = Rectangle(Point(0d, 2d), Point(2d, 2d), Point(2d, 4d), Point(0d, 4d))
    CollisionDetector.detectCollision(rect1, rect2) should equal(true)
  }

  it should "detect no collision on not intersected rects" in {
    val rect1 = Rectangle(Point(0d, 0d), Point(0d, 1d), Point(1d, 1d), Point(1d, 0d))
    val rect2 = Rectangle(Point(3d, 3d), Point(3d, 4d), Point(4d, 4d), Point(4d, 3d))
    CollisionDetector.detectCollision(rect1, rect2) should equal(false)
  }
}
