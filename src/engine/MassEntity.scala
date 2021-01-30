package engine

import math.VectorD
import processing.core.PApplet

class MassEntity(val pos: VectorD, val velocity: VectorD, val netForce: VectorD, val mass: Double, val radius: Double, val col: Int, val id: Int) {

  def stepPhysics(force: VectorD,deltaTime: Double): MassEntity ={
    val nextForce = netForce + force
    val nextVel = Integrator.stepVelocity(Integrator.stepAcceleration(nextForce,mass),velocity,deltaTime)
    val nextPos = Integrator.stepPosition(nextVel,pos,deltaTime)
    val proposedEntity = new MassEntity(nextPos,nextVel,nextForce,mass,radius,col,id)
    proposedEntity.clampPos()
  }

  def setPos(newPos: VectorD): MassEntity ={
    new MassEntity(newPos,velocity,netForce,mass,radius,col,id)
  }

  def setVelocity(newVel: VectorD): MassEntity ={
      new MassEntity(pos,newVel,netForce,mass,radius,col,id)
  }

  def draw(conext: PApplet): Unit ={
    conext.fill(0,mass.toInt,col);
    conext.circle(this.pos.xFloat(),this.pos.yFloat(),(radius * 2).toFloat)

  }

  def clampPos(): MassEntity ={
    if(pos.x > Main.SCREEN_WIDTH + 1){
      val newVel: VectorD  = VectorD(-velocity.x,velocity.y);
      val clampedPos: VectorD = VectorD(Main.SCREEN_WIDTH,pos.y)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.x < 0) {
      val newVel: VectorD  = VectorD(-velocity.x,velocity.y);
      val clampedPos: VectorD = VectorD(0,pos.y)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.y > Main.SCREEN_HEIGHT) {
      val newVel: VectorD  = VectorD(velocity.x,-velocity.y);
      val clampedPos: VectorD = VectorD(pos.x,Main.SCREEN_HEIGHT)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.y < 0) {
      val newVel: VectorD  = VectorD(velocity.x,-velocity.y);
      val clampedPos: VectorD = VectorD(pos.x,0)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else{
      this
    }
  }
}
