package engine

import math.VectorD
import processing.core.PApplet

import scala.collection.mutable.{ArrayBuffer, Map}
import java.util

class WorldState(val entities: Array[MassEntity]) {

  def calculateEnergy(): Double ={
    var total: Double = 0
    for(entity <- entities){
      total += Math.abs(entity.velocity.r())
    }
    total/entities.size
  }


  def stepMotion(deltaTime: Double): Array[MassEntity] ={
    for(entity <- entities) yield entity.stepPhysics(VectorD(0,0),deltaTime)
  }

  def resolveCollisions(): Array[MassEntity]={
    var collisions = detectIntersections(entities)
    var retList: Array[MassEntity] = entities

    retList = seperateAllIntersectingEntities(collisions)
    collisions = detectIntersections(retList)

    /*
    if(collisions.size > 0){
      retList = seperateAllIntersectingEntities(collisions)
    }
*/

  retList
  }

  def detectIntersections(entitiesToCheck: Array[MassEntity]): Map[Int,Int] = {
    val collidingPairIDs = collection.mutable.Map[Int,Int]()
    for(currentEntity <- entitiesToCheck){
      for(targetEntity <- entitiesToCheck){
        if(currentEntity.id != targetEntity.id){
          if(overlap(currentEntity,targetEntity)){
            collidingPairIDs.addOne((currentEntity.id,targetEntity.id))// += (currentEntity.id,targetEntity.id)
            PhysicsThread.numOfCols += 1
          }
        }
      }
    }
    collidingPairIDs;
  }

  def seperateAllIntersectingEntities(collidingPairIDs: Map[Int,Int]): Array[MassEntity] ={
    val seperatedEntities = collection.mutable.Map[Int,MassEntity]()
    var allEntities = ArrayBuffer[MassEntity]()

    for(pair <- collidingPairIDs){
      val newPair = solveCollision(seperateEntityPair(entities(pair._1),entities(pair._2)))
      seperatedEntities.addOne((newPair.one.id,newPair.one))//+= (newPair.one.id,newPair.one)
      seperatedEntities.addOne((newPair.two.id,newPair.two))

    }

    var itter: Int = 0;
    for(entity <- entities){
      itter += 1
      if(seperatedEntities.contains(entity.id)){ //If the entity we are looking at has been changed, get it from the list of seperated Entities
        val ent: MassEntity = seperatedEntities.get(entity.id).get
        allEntities += ent
      } else { //Otherwise get it from the list of original entities
        allEntities += entity
      }
    }
    val ret: Array[MassEntity] = allEntities.to(Array)
    ret
  }

  def seperateEntityPair(currentEntity: MassEntity, targetEntity: MassEntity): MassEntityPair ={
    val dis = overlapDistance(currentEntity,targetEntity)
    val overlap = (0.51) * (dis - currentEntity.radius - targetEntity.radius);

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

    new MassEntityPair (newC,newT)
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
      pair.one.setVelocity(VectorD(tangent.x * dotProductTanOne + normal.x * momentumOne, tangent.y * dotProductTanOne + normal.y * momentumOne).scale(0.999)),
      pair.two.setVelocity(VectorD(tangent.x * dotProductTanTwo + normal.x * momentumTwo, tangent.y * dotProductTanTwo + normal.y * momentumTwo).scale(0.999)))
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
