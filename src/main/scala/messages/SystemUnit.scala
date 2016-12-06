package messages

import akka.actor.ActorRef

case class SystemUnit(queue: ActorRef, bagggageScan: ActorRef, bodyScan: ActorRef, securityStation: ActorRef)
