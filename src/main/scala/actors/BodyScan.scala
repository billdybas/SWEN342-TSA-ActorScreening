package actors

import akka.actor.{Actor, ActorRef}

import scala.util.Random

import messages.{BodyScanStatus, BodyScanStatusRequest, EnterBodyScan, ScanReport, Startup, Shutdown, ShutdownSecurityStation}

class BodyScan(val lineNumber: Int, val securityStation: ActorRef) extends Actor {
  var isAvailable = true

  def receive = {
    case Startup => println(s"${self.path.name} starts up.")
    case Shutdown =>
      println(s"${self.path.name} shuts down.")
      securityStation ! ShutdownSecurityStation
    case BodyScanStatusRequest => sender ! BodyScanStatus(isAvailable)
    case EnterBodyScan(passenger) =>
      isAvailable = false
      securityStation ! ScanReport(passenger, randomlyPassesTest)
      isAvailable = true
    case _ => println(s"${self.path.name} Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
