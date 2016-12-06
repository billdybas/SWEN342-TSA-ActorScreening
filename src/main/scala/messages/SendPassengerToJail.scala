package messages

import akka.actor.ActorRef

case class SendPassengerToJail(val passenger: ActorRef)
