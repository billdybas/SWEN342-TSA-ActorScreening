package messages

import akka.actor.ActorRef

case class SendPassengerToQueue(val passenger: ActorRef)
