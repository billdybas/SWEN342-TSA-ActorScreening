package actors

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.HashMap

import messages.{ScanReport, LeaveSystem}

class SecurityStation(val lineNumber: Int, val jail: ActorRef) extends Actor {
  val passengerScanReports: HashMap[Passenger, Boolean] = HashMap[Passenger, Boolean]()

  def receive = {
    case ScanReport(passenger, result) =>
      if (passengerScanReports.get(passenger) == None) {
        passengerScanReports.put(passenger, result)
      } else {
        passengerScanReports.put(passenger,
          passengerScanReports.get(passenger).get && result)

        if (passengerScanReports.get(passenger) == false) {
          // Passenger failed a scan - send them to Jail
          jail ! SendPassengerToJail(passenger)
        } else {
          // Passenger passed all scans - make them leave the system
          passenger ! LeaveSystem
        }
      }
    case _ => println("Security System Received Unknown Message")
  }
}
