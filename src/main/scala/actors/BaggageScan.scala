package actors

import akka.actor.{Actor, ActorRef}

import scala.util.Random
import scala.collection.mutable.Queue

import messages.{PlaceBaggage, ReturnBaggage, ScanReport, Startup, Shutdown}

class BaggageScan(val lineNumber: Int, val securitySystem: ActorRef) extends Actor {
  val baggageQueue = Queue[Baggage]()

  def receive = {
    case Startup => println(s"Baggage Scan $lineNumber starts up.")
    case Shutdown => println(s"Baggage Scan $lineNumber shuts down.")
    case PlaceBaggage(passenger, baggage) =>
      baggageQueue :+ baggage
      securitySystem ! ScanReport(passenger, randomlyPassesTest)
      passenger ! ReturnBaggage(baggageQueue.apply(0)) // TODO: Test if this is right
    case _ => println("Baggage Scan Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
