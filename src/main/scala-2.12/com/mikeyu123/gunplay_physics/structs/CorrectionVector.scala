package com.mikeyu123.gunplay_physics.structs

case class CorrectionVector(vector: Vector, normal: Vector) extends Ordered[CorrectionVector]{
  def reverseVector: CorrectionVector = {
    CorrectionVector(vector.reverse, normal)
  }

  def normalize: CorrectionVector = {
    CorrectionVector(vector, normal.normalize)
  }

  override def compare(that: CorrectionVector): Int = {
    vector.compare(that.vector)
  }
}