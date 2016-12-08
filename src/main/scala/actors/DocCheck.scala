package actors

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.HashMap
import scala.util.Random

import messages.{GetPassenger, LeaveSystem, SendPassengerToQueue, Line}

class DocCheck(numLines: Int) extends Actor {
  val passengerQueues: HashMap[Int, ActorRef] = HashMap[Int, ActorRef]()
  var currentQueueNum: Int = 0

  def receive = {
    case Line(id, queue, baggageScan, bodyScan, securityScan) =>
      passengerQueues(id) = queue
    case GetPassenger(passenger) =>
      if (randomlyPassesTest) {
        // Send the Passenger to a queue
        val sendTo = passengerQueues(currentQueueNum)

        println(s"${self.path.name} sends ${passenger.path.name} to ${sendTo.path.name}.")
        sendTo ! SendPassengerToQueue(passenger)

        // Assignment to a queue cycles through the available queues
        currentQueueNum = (currentQueueNum + 1) % numLines
      } else {
        // Tell the Passenger to leave the system
        println(s"${self.path.name} tells ${passenger.path.name} their documents failed.")
        passenger ! LeaveSystem
      }
    case _ => println(s"${self.path.name} Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
