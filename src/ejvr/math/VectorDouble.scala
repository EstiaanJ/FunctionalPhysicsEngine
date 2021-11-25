package ejvr.math

case class VectorDouble(override val x: Double,override val y: Double) extends PointDouble(x, y) {

  def abs(): VectorDouble = {
    VectorDouble(Math.abs(x), Math.abs(y))
  }

  def crossProduct(vec: VectorDouble): Double = {
    x * vec.y - y * vec.x
  }

  def dotProduct(vec: VectorDouble): Double = {
    x * vec.x + y * vec.y
  }

  def scale(scalar: Double): VectorDouble = {
    VectorDouble(x * scalar, y * scalar)
  }

  def +(translation: VectorDouble): VectorDouble = {
    VectorDouble(x + translation.x, y + translation.y)
  }

  def +(translation: Double): VectorDouble = {
    this + VectorDouble(translation, translation)
  }

  def -(translation: VectorDouble): VectorDouble = {
    VectorDouble(x - translation.x, y - translation.y)
  }

  def difference(vec: VectorDouble): VectorDouble = {
    (this - vec).abs()
  }

  def negate(): VectorDouble = {
    VectorDouble(-x, -y)
  }

  //Most likely incorrect
  def bearingTo(vector: VectorDouble): Double = {
    flippedAtan2(vector.y - y, vector.x - x)
  }

  def distanceTo(vector: VectorDouble): Double = {
    Math.abs(this.radius() - vector.radius())
  }

  def subtractMagnitude(vector: VectorDouble): Double = {
    this.radius() - vector.radius()
  }

  def subtractBearing(vector: VectorDouble): Double = {
    theta() - vector.theta()
  }

  def tangent(): VectorDouble = {
    VectorDouble(-y, x)
  }

  override def fromPolar(mag: Double, angleIn: Double): VectorDouble = {
    val p = new PointDouble(mag,angleIn)
    VectorDouble(p.x,p.y)
  }



  override def toString: String = {
    "X: " + x + "  | Y: " + y
  }
}
