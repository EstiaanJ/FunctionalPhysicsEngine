package math

case class VectorDouble(x: Double, y: Double) {

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

  def radius(): Double = {
    Math.sqrt((x * x) + (y * y))
  }

  def theta(): Double = {
    flippedAtan2(y, x)
  }

  def thetaDegrees(): Double = {
    theta() * (180 / Math.PI)
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

  def xFloat(): Float = {
    x.toFloat
  }

  def yFloat(): Float = {
    y.toFloat
  }


  def fromPolar(mag: Double, angleIn: Double): VectorDouble = {
    val flippedAngle: Double = flipAngle(angleIn)
    VectorDouble(mag * Math.cos(flippedAngle), mag * Math.sin(flippedAngle))
  }

  def flipAngle(angle: Double): Double = {
    Math.PI / 2 - angle;
  }

  def flippedAtan2(y: Double, x: Double): Double = {
    val angle: Double = Math.atan2(y, x)
    val flippedAngle: Double = flipAngle(angle)
    if (flippedAngle >= 0) {
      flippedAngle
    }
    else {
      flippedAngle + 2 * Math.PI
    }
  }

  def copy(): VectorDouble = {
    VectorDouble(this.x, this.y)
  }

  override def toString: String = {
    "X: " + x + "  | Y: " + y
  }
}
