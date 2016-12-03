package actors

import akka.actor.Actor

import scala.collection.mutable.HashMap

import messages.ScanReport

class SecurityStation(lineNumber: Int) extends Actor {
  val passengerScanReports: HashMap[Passenger, Boolean] = new HashMap[Passenger, Boolean]()

  def receive = {
    case ScanReport(passenger, result) =>
      if (passengerScanReports.get(passenger) == None) {
        passengerScanReports.put(passenger, result)
      } else {
        passengerScanReports.put(passenger,
          passengerScanReports.get(passenger).get && result)

          // TODO: Send passenger to jail if their end result is false
          // TODO: Send passenger home if their end result is true
      }
    case _ => println("Security System Received Unknown Message")
  }
}
