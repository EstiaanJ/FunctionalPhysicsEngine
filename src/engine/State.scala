package engine

import processing.core.PApplet

import java.util

class State(val entities: Array[MassEntity]) {

  def stepMotion(deltaTime: Double): Array[MassEntity] ={
   for(entity <- entities) yield entity.stepPhysics(VectorD(0,0),deltaTime)
  }

  def checkForCollisions(): Array[MassEntity] = {
    val collidedList: util.ArrayList[MassEntity] = new util.ArrayList[MassEntity]()
    val uncolidedList: util.ArrayList[MassEntity] = new util.ArrayList[MassEntity]()
    val checkedIDList: util.ArrayList[Integer] = new util.ArrayList[Integer]()
    for(currentEntity <- entities){
      for(targetEntity <- entities){
        if(currentEntity.id != targetEntity.id) {
          if (overlap(currentEntity, targetEntity)) {
            if(!checkedIDList.contains(currentEntity.id)){
              checkedIDList.add(currentEntity.id)
              collidedList.add(solveCollision(seperate(currentEntity, targetEntity)).one)
            }
          } else {
            if(!checkedIDList.contains(currentEntity.id)){
              checkedIDList.add(currentEntity.id)
              uncolidedList.add(currentEntity)
            }
          }
        }
      }
    }
    val finalList: util.ArrayList[MassEntity] = Combiner.combineLists(collidedList,uncolidedList)

    var stateArray = new Array[MassEntity](finalList.size)
    stateArray = finalList.toArray(stateArray)
    return stateArray
  }

  def seperate(currentEntity: MassEntity, targetEntity: MassEntity): MassEntityPair ={
    val dis = overlapDistance(currentEntity,targetEntity)
    val overlap = 0.5 * (dis - currentEntity.radius - targetEntity.radius);

    var newCX = currentEntity.pos.x
    newCX -= overlap * (currentEntity.pos.x - targetEntity.pos.x) / dis
    var newCY = currentEntity.pos.y
    newCY -= overlap * (currentEntity.pos.y - targetEntity.pos.y) / dis
    val newC = currentEntity.setPos(VectorD(newCX,newCY))

    var newTX = targetEntity.pos.x
    newTX += overlap * (currentEntity.pos.x - targetEntity.pos.x) / dis
    var newTY = targetEntity.pos.y
    newTY += overlap * (currentEntity.pos.y - targetEntity.pos.y) / dis
    val newT = targetEntity.setPos(VectorD(newTX,newTY))

    new MassEntityPair(newC,newT)
  }

  def solveCollision(pair: MassEntityPair): MassEntityPair ={
    val dis = overlapDistance(pair.one, pair.two)
    val normal = VectorD((pair.two.pos.x - pair.one.pos.x) / dis, (pair.two.pos.y - pair.one.pos.y) / dis)
    val tangent = normal.tangent

    val dotProductTanOne = pair.one.velocity.dotProduct(tangent)
    val dotProductTanTwo = pair.two.velocity.dotProduct(tangent)

    val dotProductNormOne = pair.one.velocity.dotProduct(normal)
    val dotProductNormTwo = pair.two.velocity.dotProduct(normal)

    val momentumOne = (dotProductNormOne * (pair.one.mass - pair.two.mass) + 2.0 * pair.two.mass * dotProductNormTwo) / (pair.one.mass + pair.two.mass)

    val momentumTwo = (dotProductNormTwo * (pair.two.mass - pair.one.mass) + 2.0 * pair.one.mass * dotProductNormOne) / (pair.one.mass + pair.two.mass)

    new MassEntityPair (
      pair.one.setVelocity(VectorD(tangent.x * dotProductTanOne + normal.x * momentumOne, tangent.y * dotProductTanOne + normal.y * momentumOne)),
      pair.two.setVelocity(VectorD(tangent.x * dotProductTanTwo + normal.x * momentumTwo, tangent.y * dotProductTanTwo + normal.y * momentumTwo)))
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
