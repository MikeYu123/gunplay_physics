package com.mikeyu123.gunplay_physics.objects

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.structs.{Point, Rectangle}
import org.scalatest.Matchers._

/**
  * Created by mihailurcenkov on 11.07.17.
  */
class GraphicsObjectSpec extends GraphicsSpec {
  implicit val graphicsObjectEquality = graphicsObjectEq
  it should "move object correctly" in {
    val originalObject = GraphicsObject(Rectangle(Point(0, 0), Point(5, 0), Point(5, 5), Point(0, 5)), Point(2.5, 2.5))
    val movedObject = GraphicsObject(Rectangle(Point(2, -2), Point(7, -2), Point(7, 3), Point(2, 3)), Point(4.5, .5))
    originalObject.move(2, -2) should equal(movedObject)
  }

  it should "rotate object correctly" in {
    val sqrt2 = Math.sqrt(2)
    val originalObject = GraphicsObject(Rectangle(Point(0, 0), Point(5, 0), Point(5, 5), Point(0, 5)), Point(0, 0))
    val rotatedObject = GraphicsObject(Rectangle(Point(0, 0),
                                        Point(5 / sqrt2, 5 / sqrt2),
                                        Point(0, 5 * sqrt2),
                                        Point(-5 / sqrt2, 5 / sqrt2)), Point(0, 0))
    originalObject.rotate(45d.toRadians) should equal(rotatedObject)
  }
}
