package actors

import akka.actor.{Actor, ActorRef}

import scala.util.Random
import scala.collection.mutable.Queue

import messages.{PlaceBaggage, ReturnBaggage, ScanReport, Startup, Shutdown}

class BaggageScan(val lineNumber: Int, val securityStation: ActorRef) extends Actor {
  val baggageQueue = Queue[Baggage]()

  def receive = {
    case Startup =>
      println(s"${self.path.name} starts up.")
    case Shutdown =>
      // TODO: Process all Passengers in the Queue first
      println(s"${self.path.name} shuts down.")
      securityStation forward Shutdown
    case PlaceBaggage(passenger, baggage) =>
      baggageQueue += baggage
      println(s"${self.path.name} sends a Scan Report about ${passenger.path.name}.")
      securityStation ! ScanReport(passenger, randomlyPassesTest)
      passenger ! ReturnBaggage(baggageQueue.dequeue())
    case _ =>
      println(s"${self.path.name} Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
