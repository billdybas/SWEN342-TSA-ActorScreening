package actors

import akka.actor.Actor

import scala.collection.mutable.HashMap
import scala.util.Random

import messages.{GetPassenger, LeaveSystem, SendPassengerToQueue, SystemUnit}

class DocCheck(numSystemUnits: Int) extends Actor {
  val passengerQueues: HashMap[Int, ActorRef] = HashMap[Int, ActorRef]()
  var currentQueueNum: Int = 0

  def receive = {
    case SystemUnit(id, queue, baggageScan, bodyScan, securityScan) =>
      passengerQueues.put(id, queue)
    case GetPassenger(passenger) =>
      if (randomlyPassesTest) {
        // Send the Passenger to a queue
        passengerQueues.get(currentQueueNum).get ! SendPassengerToQueue(passenger)
        // Assignment to a queue cycles through the available queues
        currentQueueNum = (currentQueueNum + 1) % numSystemUnits
      } else {
        // Tell the Passenger to leave the system
        passenger ! LeaveSystem
      }
    case _ => println("Document Check Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
