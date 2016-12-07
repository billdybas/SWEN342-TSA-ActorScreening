package actors

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import akka.routing._

class DocCheck extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
    case GetPassenger() => 
    	if(randomlyPassesTest) {

    	} else {
    		//Tell the passenger to leave the system
    		passenger ! leaveSystem()
    	}
  }

  def randomlyPassesTest: Boolean = {
    // Passes the test 80% of the time
    val r = Random
    r.nextDouble() > 0.2
  }
}
