package actors

import akka.actor.{Actor, ActorRef}

import scala.util.Random

import messages.{BodyScanStatus, BodyScanStatusRequest, EnterBodyScan, ScanReport, Startup, Shutdown}

class BodyScan(val lineNumber: Int, val securitySystem: ActorRef) extends Actor {
  var isAvailable = true

  def receive = {
    case Startup => println(s"Body Scan $lineNumber starts up.")
    case Shutdown => println(s"Body Scan $lineNumber shuts down.")
    case BodyScanStatusRequest => sender ! BodyScanStatus(isAvailable)
    case EnterBodyScan(passenger) =>
      isAvailable = false
      securitySystem ! ScanReport(passenger, randomlyPassesTest)
      isAvailable = true
    case _ => println("Body Scan Received Unknown Message")
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
