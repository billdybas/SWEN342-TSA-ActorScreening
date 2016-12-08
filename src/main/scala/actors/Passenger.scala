package actors

import akka.actor.{Actor, ActorRef}

import messages.{PlaceBaggage, ReturnBaggage, LeaveSystem, GoToDetention, BaggageScanReady, EnterBodyScan, ScanReport}

class Baggage()

class Passenger(val baggage: Baggage) extends Actor {

  def receive = {
    case BaggageScanReady(baggageScan) =>
      println(s"${self.path.name} places their baggage on ${baggageScan.path.name}.")
      baggageScan ! PlaceBaggage(self, baggage)
    case ReturnBaggage(baggage) =>
      println(s"${self.path.name} has their baggage returned.")
    case LeaveSystem =>
      println(s"${self.path.name} leaves the system.")
    case GoToDetention =>
      println(s"${self.path.name} goes to detention.")
    case _ =>
      println(s"${self.path.name} Received Unknown Message ${sender}")
  }
}
