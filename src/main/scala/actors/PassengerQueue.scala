package actors

import akka.actor.{Actor, ActorRef}
import scala.collection.mutable.Queue

import messages.{SendPassengerToQueue, BaggageScanReady, BodyScanStatusRequest, EnterBodyScan, BodyScanStatus}

class PassengerQueue(baggageScan: ActorRef, bodyScan: ActorRef) extends Actor {
  val passengerQueue = Queue[ActorRef]()

  def receive = {
    case SendPassengerToQueue(passenger) =>
      passengerQueue += passenger
      // Tell the Passenger they can place their Baggage
      passenger ! BaggageScanReady(baggageScan)
      bodyScan ! BodyScanStatusRequest
    case BodyScanStatus(isAvailable) =>
      if (isAvailable) {
        bodyScan ! EnterBodyScan(passengerQueue.dequeue())
      } else {
        bodyScan ! BodyScanStatusRequest
      }
    case _ => println(s"${self.path.name} Received Unknown Message")
  }
}
