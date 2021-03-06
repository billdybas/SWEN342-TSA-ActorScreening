package actors

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.HashMap

import messages.{ScanReport, LeaveSystem, SendPassengerToJail, Shutdown, SecurityStationFinished}

class SecurityStation(val lineNumber: Int, val jail: ActorRef) extends Actor {
  val passengerScanReports: HashMap[ActorRef, Boolean] = HashMap[ActorRef, Boolean]()
  var shutdownRequests: Int = 0

  def receive = {
    case ScanReport(passenger, result) =>
      if (passengerScanReports.get(passenger) == None) {
        println(s"${self.path.name} receives its first scan report for ${passenger.path.name}.")
        passengerScanReports.put(passenger, result)
      } else {
        println(s"${self.path.name} receives its second scan report for ${passenger.path.name}.")
        passengerScanReports.put(passenger,
          passengerScanReports.get(passenger).get && result)

        if (passengerScanReports.get(passenger).get == false) {
          // Passenger failed a scan - send them to Jail
          jail ! SendPassengerToJail(passenger)
        } else {
          // Passenger passed all scans - make them leave the system
          println(s"${self.path.name} tells ${passenger.path.name} to leave.")
          passenger ! LeaveSystem
        }
      }
    case Shutdown =>
      // TODO: This will only check if two messages have been received.
      // This could be made better by checking the types of each sender,
      // making sure both have sent messages, so that for whatever reason
      // an actor sends a duplicate message, the security station can handle it
      shutdownRequests = shutdownRequests + 1

      if (shutdownRequests == 2) {
        println(s"${self.path.name} shuts down.")
        jail ! SecurityStationFinished
        // TODO: sender ! SecurityStationFinished // ?
      }
    case _ =>
      println(s"${self.path.name} Received Unknown Message")
  }
}
