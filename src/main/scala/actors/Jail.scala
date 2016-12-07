package actors

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.Queue

import messages.{SendPassengerToJail, SecurityStationFinished, GoToDetention}

class Jail(val numSecurityStations: Int) extends Actor {
  val passengerQueue = Queue[ActorRef]()
  var numFinishedSecurityStations: Int = 0

  def receive = {
    case SendPassengerToJail(passenger) =>
      passengerQueue :+ passenger
    case SecurityStationFinished =>
      numFinishedSecurityStations = numFinishedSecurityStations + 1

      if (numFinishedSecurityStations == numSecurityStations) {
        passengerQueue.foreach((p: ActorRef) => p ! GoToDetention)
      }
    case _ => println(s"${self.path.name} Received Unknown Message")
  }
}
