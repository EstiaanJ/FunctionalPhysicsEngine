package engine

import processing.core.PApplet

class MassEntity(val pos: VectorD,val velocity: VectorD,val netForce: VectorD,val mass: Double,val radius: Double,val id: Int) {

  def stepPhysics(force: VectorD,deltaTime: Double): MassEntity ={
    val nextForce = netForce + force
    val nextVel = Integrator.stepVelocity(Integrator.stepAcceleration(nextForce,mass),velocity,deltaTime)
    val nextPos = Integrator.stepPosition(nextVel,pos,deltaTime)
    new MassEntity(nextPos,nextVel,nextForce,mass,radius,id)
  }


  def setPos(newPos: VectorD): MassEntity ={
    new MassEntity(newPos,velocity,netForce,mass,radius,id)
  }

  def setVelocity(newVel: VectorD): MassEntity ={
      new MassEntity(pos,newVel,netForce,mass,radius,id)
  }

  def draw(conext: PApplet): Unit ={
    conext.circle(this.pos.xFloat(),this.pos.yFloat(),10)
  }
}
