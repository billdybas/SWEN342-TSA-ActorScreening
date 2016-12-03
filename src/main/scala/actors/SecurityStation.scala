package actors

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class SecurityStation extends Actor {
  // What line number
  // body and baggage scan results for each passenger

  def receive = {




    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}
