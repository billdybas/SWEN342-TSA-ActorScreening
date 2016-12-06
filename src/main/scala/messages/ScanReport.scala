package messages

import akka.actor.ActorRef

case class ScanReport(val passenger: ActorRef, val scanResult: Boolean)
