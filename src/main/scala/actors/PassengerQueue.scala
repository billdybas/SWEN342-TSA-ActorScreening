package actors

import akka.actor.{Actor, ActorRef}

import messages.{SendPassengerToQueue, BaggageScanReady}

class PassengerQueue(baggageScan: ActorRef, bodyScan: ActorRef) extends Actor {

  def receive = {
    case SendPassengerToQueue(passenger) =>
      // Tell the Passenger they can place their Baggage
      passenger ! BaggageScanReady(baggageScan)

      // TODO: Wait for the Body Scan to be ready and then tell the Passenger they can enter

    case _ => println(s"${self.path.name} Received Unknown Message")
  }
}
