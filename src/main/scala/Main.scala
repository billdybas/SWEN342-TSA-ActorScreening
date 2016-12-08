
import akka.actor.{ActorSystem, Props, ActorRef}

import scala.collection.mutable.Queue

import actors._
import messages.{Line, Startup, Shutdown, GetPassenger}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("TSA-Actor-Screening")

    val NUM_LINES: Int = 1
    val NUM_PASSENGERS: Int = 7

    val scanners = Queue[ActorRef]()

    val documentCheck = system.actorOf(Props(new DocCheck(NUM_LINES)), name = "Document-Check")
    val jail = system.actorOf(Props(new Jail(NUM_LINES)), name = "Jail")

    for (i <- 0 to NUM_LINES) {
      val securityStation = system.actorOf(Props(new SecurityStation(i, jail)), name = s"Security-Station-${i}")

      val baggageScan = system.actorOf(Props(new BaggageScan(i, securityStation)), name = s"Baggage-Scan-${i}")
      val bodyScan = system.actorOf(Props(new BodyScan(i, securityStation)), name = s"Body-Scan-${i}")

      scanners += baggageScan
      scanners += bodyScan

      val queue = system.actorOf(Props(new PassengerQueue(baggageScan, bodyScan)), name = s"Queue-${i}")

      baggageScan ! Startup
      bodyScan ! Startup

      documentCheck ! Line(i, queue, baggageScan, bodyScan, securityStation)
    }

    for (i <- 0 to NUM_PASSENGERS) {
      val passenger = system.actorOf(Props(new Passenger(new Baggage())), name = s"Passenger-${i}")

      documentCheck ! GetPassenger(passenger)
    }
Thread.sleep(5000)
    scanners.foreach((s: ActorRef) => s ! Shutdown)

    // TODO: Wait for all security stations to say they're shutdown

    // system.terminate()
  }
}
