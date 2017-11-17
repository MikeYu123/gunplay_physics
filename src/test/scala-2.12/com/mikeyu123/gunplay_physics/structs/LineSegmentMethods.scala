package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.GraphicsSpec
import com.mikeyu123.gunplay_physics.util.IntersectionDetector
import org.scalatest.Matchers._

class LineSegmentMethods extends GraphicsSpec {

  it should "get distance test" in {
    val ls = LineSegment(Point(0, 0), Point(2, 0))
    val p = Point(0, 2)
    ls.distance(p) should equal {
      2
    }
  }

  it should "get distance test 0" in {
    val ls = LineSegment(Point(0, 0), Point(0, 2))
    val p = Point(2, 0)
    ls.distance(p) should equal {
      2
    }
  }

  it should "get distance test 1" in {
    val ls = LineSegment(Point(0, 0), Point(2, 0))
    val p = Point(0, -2)
    ls.distance(p) should equal {
      2
    }
  }

  it should "get distance test 2" in {
    val ls = LineSegment(Point(0, 0), Point(0, -2))
    val p = Point(-2, 0)
    ls.distance(p) should equal {
      2
    }
  }

  it should "has test 0" in {
    val ls = LineSegment(Point(0, 0), Point(0, -2))
    ls.has(Point(0, 0)) should equal {
      true
    }
  }

  it should "normal test 0" in {
    LineSegment(Point(0, 0), Point(2, 0)).normal should equal {
      Vector(0, 1)
    }
    LineSegment(Point(0, 0), Point(1, 2)).normal should equal {
      Vector(-2, 1) / math.sqrt(5)
    }
    LineSegment(Point(0, 0), Point(0, 2)).normal should equal {
      Vector(-1, 0)
    }
    LineSegment(Point(0, 0), Point(-3, 2)).normal should equal {
      Vector(-2, -3) / math.sqrt(13)
    }
    LineSegment(Point(0, 0), Point(-3, 0)).normal should equal {
      Vector(0, -1)
    }
    LineSegment(Point(0, 0), Point(-2, -2)).normal should equal {
      Vector(1, -1) / math.sqrt(2)
    }
    LineSegment(Point(0, 0), Point(0, -2)).normal should equal {
      Vector(1, 0)
    }
    LineSegment(Point(0, 0), Point(2, -2)).normal should equal {
      Vector(1, 1) / math.sqrt(2)
    }
  }

  it should "normal test 1" in {
    LineSegment(Point(0, 0), Point(2, 0)).normal(Point(1, 3)) should equal {
      Vector(0, 3)
    }
    LineSegment(Point(0, 0), Point(4, 3)).normal(Point(0.5, 3.5)) should equal {
      Vector(-1.5, 2)
    }
  }

  it should "projectsOn line test 0" in {
    val l = LineSegment(Point(0, 0), Point(5, 0))
    l.projectsOn(Point(3, 5)) should equal {
      true
    }
  }

  it should "projectsOn line test 1" in {
    val l = LineSegment(Point(0, 0), Point(5, 0))
    l.projectsOn(Point(6, 5)) should equal {
      false
    }
  }

  it should "intersection line test 0" in {
    val l0 = LineSegment(Point(0, 0), Point(5, 0))
    val l1 = LineSegment(Point(0, 0), Point(5, 1))
    l0.intersects(l1) should equal {
      true
    }
  }

  it should "intersection line test 1" in {
    val l0 = LineSegment(Point(0, 0), Point(5, 0))
    val l1 = LineSegment(Point(3, 0), Point(5, 1))
    l1.intersects(l0) should equal {
      true
    }
  }

  it should "intersection line test 2" in {
    val l0 = LineSegment(Point(0, 0), Point(5, 0))
    val l1 = LineSegment(Point(1, -1), Point(5, 1))
    l1.intersects(l0) should equal {
      true
    }
  }

  it should "intersection line test 3" in {
    val l0 = LineSegment(Point(0, 0), Point(5, 0))
    val l1 = LineSegment(Point(1, 1), Point(5, 1))
    val res = l1.intersects(l0)
    res should equal {
      false
    }
  }

  it should "intersection line test 4" in {
    val l0 = LineSegment(Point(1, 1), Point(5, 3))
    val l1 = LineSegment(Point(2, 3), Point(5, 0))
    val res = l1.intersection(l0)
    res should equal {
      Point(3, 2)
    }
  }

  it should "intersection line test 5" in {
    val l0 = LineSegment(Point(0, 0), Point(5, 0))
    val l1 = LineSegment(Point(2, 1), Point(3, 2))
    val res = l1.intersection(l0)
    res should equal {
      Point(1, 0)
    }
  }

  it should "intersection line test 6" in {
    val l0 = LineSegment(Point(0, 0), Point(1, 1))
    val l1 = LineSegment(Point(1, 0), Point(2, 1))
    val res = l1.intersection(l0)
    res should equal {
      Point(Double.NegativeInfinity, Double.NegativeInfinity)
    }
  }

  it should "intersection line test 7" in {
    val l0 = LineSegment(Point(0, 0), Point(1, 1))
    val l1 = LineSegment(Point(1, 0), Point(2, 1))
    val res = l1.willIntersect(l0)
    res should equal {
      false
    }
  }

  it should "intersection line test 8" in {
    val l0 = LineSegment(Point(1, 2), Point(2, 1))
    val l1 = LineSegment(Point(0, 0), Point(5, 0))
    val res = l1.willIntersect(l0)
    res should equal {
      false
    }
  }

  it should "intersection line test 80" in {
    val l0 = LineSegment(Point(1, 2), Point(2, 1))
    val l1 = LineSegment(Point(0, 0), Point(5, 0))
    val res = l0.willIntersect(l1)
    res should equal {
      true
    }
  }

  it should "intersection line test 9" in {
    val l0 = LineSegment(Point(2, 1),Point(1, 2))
    val l1 = LineSegment(Point(0, 0), Point(5, 0))
    val res = l0.willIntersect(l1)
    res should equal {
      false
    }
  }

}