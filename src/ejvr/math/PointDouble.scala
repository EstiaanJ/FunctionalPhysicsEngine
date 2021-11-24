package ejvr.math

class PointDouble(val x: Double, val y:Double) {
  def xFloat(): Float = {
    x.toFloat
  }

  def yFloat(): Float = {
    y.toFloat
  }

  def radius(): Double = {
    Math.sqrt((x * x) + (y * y))
  }

  def distanceTo(vec: PointDouble): Double = {
    Math.sqrt((this.x - vec.x)*(this.x - vec.x) + (this.y - vec.y) * (this.y - vec.y))
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

  def theta(): Double = {
    flippedAtan2(y, x)
  }

  def thetaDegrees(): Double = {
    theta() * (180 / Math.PI)
  }

  def fromPolar(mag: Double, angleIn: Double): PointDouble = {
    val flippedAngle: Double = flipAngle(angleIn)
    new PointDouble(mag * Math.cos(flippedAngle), mag * Math.sin(flippedAngle))
  }


  def copy(): PointDouble = {
    new PointDouble(this.x, this.y)
  }
}
