package engine

import math.VectorDouble

object Integrator {

  def stepPosition(velocity: VectorDouble, pos: VectorDouble, time: Double): VectorDouble = {
    pos + (velocity.scale(time))
  }

  def stepVelocity(acceleration: VectorDouble, velocity: VectorDouble, time: Double): VectorDouble = {
    velocity + (acceleration.scale(time))
  }

  def stepAcceleration(netForce: VectorDouble, mass: Double): VectorDouble = {
    VectorDouble(netForce.x/mass,netForce.y/mass)
  }


  def stepRotation(angularVelocity: Double, rotation: Double, time: Double): Double = {
    rotation + (angularVelocity/time)
  }

  def stepAngluarVelocity(angularAcceleration: Double, angularVelocity: Double, time: Double): Double = {
    angularVelocity + (angularAcceleration/time)
  }

  def stepAngularAcceleration(torque: Double, momentOfInertia: Double): Double = {
    torque/momentOfInertia
  }

  def getPosition(netForce: VectorDouble, mass: Double, lastVelocity: VectorDouble, lastPos: VectorDouble, time: Double): VectorDouble = {
    stepPosition(stepVelocity(stepAcceleration(netForce,mass),lastVelocity,time),lastPos,time)
  }

  def getRotation(torque: Double, momentOfInertia: Double, lastAV: Double, lastPos: Double, time: Double): Double = {
    stepRotation(stepAngluarVelocity(stepAngularAcceleration(torque,momentOfInertia),lastAV,time),lastPos,time)
  }

  /*
    def getPositionReal(netForce: VectorD, mass: Double, lastVelocity: VectorD, lastPos: VectorD, time: Double): VectorD = {
      stepPosition(stepVelocity(stepAcceleration(netForce,mass),lastVelocity,time),lastPos,time)
    }
    */
}
