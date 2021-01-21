package engine

import processing.core.PApplet

class MassEntity(val pos: VectorD,val velocity: VectorD,val netForce: VectorD,val mass: Double,val radius: Double,val id: Int) {

  def stepPhysics(force: VectorD,deltaTime: Double): MassEntity ={
    val nextForce = netForce + force
    val nextVel = Integrator.stepVelocity(Integrator.stepAcceleration(nextForce,mass),velocity,deltaTime)
    val nextPos = Integrator.stepPosition(nextVel,pos,deltaTime)
    new MassEntity(nextPos,nextVel,nextForce,mass,radius,id)
  }


  def newPos(newPos: VectorD): MassEntity ={
    new MassEntity(newPos,velocity,netForce,mass,radius,id)
  }
  /*
    def newFromVel(newVel: VectorD, old: MassEntity): MassEntity ={
      new MassEntity(old.pos,newVel,old.netForce,old.mass,old.radius,old.id)
    }
  */
  def draw(conext: PApplet): Unit ={
    conext.circle(this.pos.xFloat(),this.pos.yFloat(),10)
  }
}
