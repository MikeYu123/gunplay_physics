package com.mikeyu123.gunplay_physics.structs

import com.mikeyu123.gunplay_physics.objects.PhysicsObject

case class Contact(a: PhysicsObject, b: PhysicsObject, contactPoint: Point, normal: LineSegment) {


}
