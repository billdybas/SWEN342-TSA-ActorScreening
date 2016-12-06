package actors

import akka.actor.{Actor, ActorRef}

import messages.{ReturnBaggage, LeaveSystem, GoToDetention}

class Baggage()

class Passenger(val baggage: Baggage) extends Actor {

  def receive = {
    case ReturnBaggage => println(s"${self.path.name} has their baggage returned.") // TODO: Check if they received the right baggage?
    case LeaveSystem => println(s"${self.path.name} leaves the system.")
    case GoToDetention => println(s"${self.path.name} goes to detention.")
    case _ => println(s"${self.path.name} Received Unknown Message")
  }
}
