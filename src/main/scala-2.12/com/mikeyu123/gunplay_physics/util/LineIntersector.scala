package com.mikeyu123.gunplay_physics.util
import com.mikeyu123.gunplay_physics.structs.{LineSegment, Point}
/**
  * Created by mihailurcenkov on 11.07.17.
  */
object LineIntersector {
  def intersectLines(line1: LineSegment, line2: LineSegment) : Option[Point] = {
    val denominator: Double =
      (line1.point1.x - line1.point2.x) * (line2.point1.y - line2.point2.y) -
        (line2.point1.x - line2.point2.x) * (line1.point1.y - line1.point2.y)
    denominator match {
//        TODO check on equal lines
      case .0d => None
      case _ =>
//        Algo for line-line intersection given two points each
//        TODO decompose
        val px: Double = (line1.point1.x * line1.point2.y - line1.point2.x * line1.point1.y) * (line2.point1.x - line2.point2.x) -
          (line2.point1.x * line2.point2.y - line2.point2.x * line2.point1.y) * (line1.point1.x - line1.point2.x)
        val py: Double = (line1.point1.x * line1.point2.y - line1.point2.x * line1.point1.y) * (line2.point1.y - line2.point2.y) -
          (line2.point1.x * line2.point2.y - line2.point2.x * line2.point1.y) * (line1.point1.y - line1.point2.y)
        Some(Point(px / denominator, py / denominator))
    }
  }
}
