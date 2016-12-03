package actors

import akka.actor.Actor

class Baggage(val passenger: Passenger)

class Passenger(val baggage: Baggage) extends Actor {

  def receive = {
    case _ => println("Passenger Received Unknown Message")
  }
}
