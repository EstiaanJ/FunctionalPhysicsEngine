package engine

import processing.core.PApplet

import java.util

class State(val entities: Array[MassEntity]) {

  def stepMotion(deltaTime: Double): Array[MassEntity] ={
   for(entity <- entities) yield entity.stepPhysics(VectorD(0,0),deltaTime)
  }

  def checkForCollisions(): Unit = {
    val pairList: util.ArrayList[MassEntityPair] = new util.ArrayList[MassEntityPair]()
    val newList: util.ArrayList[MassEntity] = new util.ArrayList[MassEntity]()
    for(currentEntity <- entities){
      for(targetEntity <- entities){
        if(currentEntity.id != targetEntity.id) {
          if(overlap(currentEntity,targetEntity)){
            val dis = overlapDistance(currentEntity,targetEntity)
            val overlap = 0.5 * (dis - currentEntity.radius - targetEntity.radius);

            var newCX = currentEntity.pos.x
            newCX -= overlap * (currentEntity.pos.x - targetEntity.pos.x) / dis
            var newCY = currentEntity.pos.y
            newCY -= overlap * (currentEntity.pos.y - targetEntity.pos.y) / dis
            val newC = currentEntity.newPos(VectorD(newCX,newCY))

            var newTX = targetEntity.pos.x
            newTX += overlap * (currentEntity.pos.x - targetEntity.pos.x) / dis
            var newTY = targetEntity.pos.y
            newTY += overlap * (currentEntity.pos.y - targetEntity.pos.y) / dis
            val newT = targetEntity.newPos(VectorD(newTX,newTY))

            pairList.add(new MassEntityPair(newC,newT))
          }
        }
      }
    }
  }

  def draw(context: PApplet): Unit = {
    for(entity <- entities) entity.draw(context)
  }

  def overlap(one: MassEntity, two: MassEntity): Boolean = {
    val radSqr = (one.radius + two.radius) * (one.radius + two.radius);
    if(overlapDistanceSquared(one,two) <= radSqr){
      true
    } else{
      false
    }
  }

  def overlapDistance(one: MassEntity, two: MassEntity): Double = {
    Math.sqrt(overlapDistanceSquared(one,two))
  }

  def overlapDistanceSquared(one: MassEntity, two: MassEntity): Double ={
    ((one.pos.x - two.pos.x) * (one.pos.x - two.pos.x)) + ((one.pos.y - two.pos.y) * (one.pos.y - two.pos.y))
  }
}
