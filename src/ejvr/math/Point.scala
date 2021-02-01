package ejvr.math

class Point(x: Double, y:Double) {
  def xFloat(): Float = {
    x.toFloat
  }

  def yFloat(): Float = {
    y.toFloat
  }

  def copy(): VectorDouble = {
    VectorDouble(this.x, this.y)
  }
}
