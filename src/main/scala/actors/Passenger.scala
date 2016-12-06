package actors

import akka.actor.{Actor, ActorRef}

import messages.{ReturnBaggage, LeaveSystem}

class Baggage(val passenger: ActorRef)

class Passenger(val baggage: Baggage) extends Actor {

  def receive = {
    case _ => println("Passenger Received Unknown Message")
  }
}
