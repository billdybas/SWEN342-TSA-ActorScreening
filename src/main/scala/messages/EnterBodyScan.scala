package messages

import akka.actor.ActorRef

case class EnterBodyScan(val passenger: ActorRef)
