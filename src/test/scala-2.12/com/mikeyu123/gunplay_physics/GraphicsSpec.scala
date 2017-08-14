package com.mikeyu123.gunplay_physics

import com.mikeyu123.gunplay_physics.objects.GraphicsObject
import com.mikeyu123.gunplay_physics.structs.{Point, Rectangle}
import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FlatSpec

/**
  * Created by mihailurcenkov on 12.07.17.
  */
//TODO: refactor and determine whether this can be achieved somehow different
trait GraphicsSpec extends FlatSpec {
  def doubleEq: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.0001)

  def pointEq: Equality[Point] = (a: Point, b: Any) => {
    implicit val equality: Equality[Double] = doubleEq
    b match {
      case p: Point => a.x === p.x && a.y === p.y
      case _ => false
    }
  }

  def rectangleEq: Equality[Rectangle] = (a: Rectangle, b: Any) => {
    implicit val equality: Equality[Point] = pointEq
    b match {
      case p: Rectangle => a.point1 === p.point1 &&
        a.point2 === p.point2 &&
        a.point3 === p.point3 &&
        a.point4 === p.point4
      case _ => false
    }
  }

  def graphicsObjectEq: Equality[GraphicsObject] = (a: GraphicsObject, b: Any) => {
    implicit val rectangleEquality: Equality[Rectangle] = rectangleEq
    implicit val pointEquality: Equality[Point] = pointEq
    b match {
      case p: GraphicsObject => a.rectangle === p.rectangle &&
        a.center === p.center
      case _ => false
    }
  }
}
