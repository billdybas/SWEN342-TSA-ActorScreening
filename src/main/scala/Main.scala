
import akka.actor.{ActorSystem, Props}
import actors._
import messages.{Line, Startup, Shutdown}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("TSA-Actor-Screening")

    val numLines: Int = 2 // TODO: Command-line arg?

    val documentCheck = system.actorOf(Props(new DocCheck(numLines)), name = "Document Check")
    val jail = system.actorOf(Props(new Jail(numLines)), name = "Jail")

    for (i <- 1 to 10) {
      val queue = system.actorOf(Props(new PassengerQueue()), name = s"Passenger ${i}")

      val securityStation = system.actorOf(Props(new SecurityStation(i, jail)), name = s"Security Station ${i}")

      val baggageScan = system.actorOf(Props(new BaggageScan(i, securityStation)), name = s"Baggage Scan ${i}")
      val bodyScan = system.actorOf(Props(new BodyScan(i, securityStation)), name = s"Body Scan ${i}")

      baggageScan ! Startup
      bodyScan ! Startup

      documentCheck ! Line(i, queue, baggageScan, bodyScan, securityStation)
    }

    // TODO: Let the document check start accepting passengers
    //
    // TODO: Keep a timer of the entire day
    //
    // TODO: Send a shutdown message to everyone who needs one and when
    // receive message back saying they're all done, shutdown the entire actor system

    system.terminate()
  }
}
