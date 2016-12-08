package actors

import akka.actor.{Actor, ActorRef}

import scala.util.Random

import messages.{BodyScanStatus, BodyScanStatusRequest, EnterBodyScan, ScanReport, Startup, Shutdown}

class BodyScan(val lineNumber: Int, val securityStation: ActorRef) extends Actor {
  var isAvailable = true

  def receive = {
    case Startup =>
      println(s"${self.path.name} starts up.")
    case Shutdown =>
      // TODO: Process all Passengers in the Queue first
      println(s"${self.path.name} shuts down.")
      securityStation forward Shutdown
    case BodyScanStatusRequest =>
      sender ! BodyScanStatus(isAvailable)
    case EnterBodyScan(passenger) =>
      isAvailable = false
      println(s"${self.path.name} sends a Scan Report about ${passenger.path.name}.")
      securityStation ! ScanReport(passenger, randomlyPassesTest)
      isAvailable = true
    case _ =>
      println(s"${self.path.name} Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
