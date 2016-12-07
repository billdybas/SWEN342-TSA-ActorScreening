package messages

import akka.actor.ActorRef

case class GetPassenger(val passenger: ActorRef)
