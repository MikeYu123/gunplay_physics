package com.mikeyu123.gunplay_physics.util
import com.mikeyu123.gunplay_physics.structs.{LineSegment, Point}
/**
  * Created by mihailurcenkov on 11.07.17.
  */
object LineIntersector {
  def intersectLines(line1: LineSegment, line2: LineSegment) : Option[Point] = {
    val denominator: Double =
      (line1.start.x - line1.end.x) * (line2.start.y - line2.end.y) -
        (line2.start.x - line2.end.x) * (line1.start.y - line1.end.y)
    denominator match {
//        TODO check on equal lines
      case .0d => None
      case _ =>
//        Algo for line-line intersection given two points each
//        TODO decompose
        val px: Double = (line1.start.x * line1.end.y - line1.end.x * line1.start.y) * (line2.start.x - line2.end.x) -
          (line2.start.x * line2.end.y - line2.end.x * line2.start.y) * (line1.start.x - line1.end.x)
        val py: Double = (line1.start.x * line1.end.y - line1.end.x * line1.start.y) * (line2.start.y - line2.end.y) -
          (line2.start.x * line2.end.y - line2.end.x * line2.start.y) * (line1.start.y - line1.end.y)
        Some(Point(px / denominator, py / denominator))
    }
  }
}
