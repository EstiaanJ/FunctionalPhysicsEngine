package ejvr.engine

import ejvr.math.{VectorDouble, VectorIntegration, PointDouble}
import processing.core.PApplet

class MassEntity(val pos: VectorDouble, val velocity: VectorDouble, val netForce: VectorDouble, val mass: Double, val radius: Double, val col: Int, val id: Int) {

  def stepPhysics(force: VectorDouble, deltaTime: Double): MassEntity ={
    val nextForce = netForce + force
    val nextVel = VectorIntegration.stepVelocity(VectorIntegration.stepAcceleration(nextForce,mass),velocity,deltaTime)
    val nextPos = VectorIntegration.stepPosition(nextVel,pos,deltaTime)
    val proposedEntity = new MassEntity(nextPos,nextVel,nextForce,mass,radius,col,id)
    proposedEntity
    //proposedEntity.clampPos() THIS IS  TEST CODE

  }

  def setPos(newPos: VectorDouble): MassEntity ={
    new MassEntity(newPos,velocity,netForce,mass,radius,col,id)
  }

  def setVelocity(newVel: VectorDouble): MassEntity ={
      new MassEntity(pos,newVel,netForce,mass,radius,col,id)
  }

  def draw(conext: PApplet): Unit ={
    conext.fill(0,mass.toInt,col);
    conext.circle(this.pos.xFloat(),this.pos.yFloat(),(radius * 2).toFloat)

  }

  def contains(p: PointDouble): Boolean = {
    PhysicsUtil.circleContainsPoint(p,this.pos,this.radius)

  }
/* THIS IS TEST CODE
  def clampPos(): MassEntity ={
    if(pos.x > Main.SCREEN_WIDTH + 1){
      val newVel: VectorDouble  = VectorDouble(-velocity.x,velocity.y);
      val clampedPos: VectorDouble = VectorDouble(Main.SCREEN_WIDTH,pos.y)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.x < 0) {
      val newVel: VectorDouble  = VectorDouble(-velocity.x,velocity.y);
      val clampedPos: VectorDouble = VectorDouble(0,pos.y)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.y > Main.SCREEN_HEIGHT) {
      val newVel: VectorDouble  = VectorDouble(velocity.x,-velocity.y);
      val clampedPos: VectorDouble = VectorDouble(pos.x,Main.SCREEN_HEIGHT)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else if(pos.y < 0) {
      val newVel: VectorDouble  = VectorDouble(velocity.x,-velocity.y);
      val clampedPos: VectorDouble = VectorDouble(pos.x,0)
      new MassEntity(clampedPos,newVel,netForce,mass,radius,col,id)
    } else{
      this
    }
  }

 */
}
