package messages

import akka.actor.ActorRef

case class Line(id: Int, queue: ActorRef, bagggageScan: ActorRef, bodyScan: ActorRef, securityStation: ActorRef)
