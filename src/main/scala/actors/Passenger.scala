package actors

import akka.actor.{Actor, ActorRef}

import messages.{PlaceBaggage, ReturnBaggage, LeaveSystem, GoToDetention, BaggageScanReady, EnterBodyScan, ScanReport}

class Baggage()

class Passenger(val baggage: Baggage) extends Actor {

  def receive = {
    case BaggageScanReady(baggageScan) =>
      baggageScan ! PlaceBaggage(self, baggage)
    // TODO: Case for entering and leaving the Body Scan
    case ReturnBaggage => println(s"${self.path.name} has their baggage returned.") // TODO: Check if they received the right baggage?
    case LeaveSystem => println(s"${self.path.name} leaves the system.")
    case GoToDetention => println(s"${self.path.name} goes to detention.")
    case _ => println(s"${self.path.name} Received Unknown Message")
  }
}
