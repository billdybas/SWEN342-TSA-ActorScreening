package messages

import akka.actor.ActorRef

case class SystemUnit(id: Int, queue: ActorRef, bagggageScan: ActorRef, bodyScan: ActorRef, securityStation: ActorRef)
