package messages

import akka.actor.ActorRef

case class SystemUnit(queue: ActorRef, bagScan: ActorRef, bodyScan: ActorRef, securityStation: ActorRef)
