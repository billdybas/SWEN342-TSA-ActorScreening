package messages

import akka.actor.ActorRef

import actors.Baggage

case class BaggageScanReady(baggageScan: ActorRef)

case class BodyScanStatus(val available: Boolean)

case class BodyScanStatusRequest()

case class EnterBodyScan(val passenger: ActorRef)

case class GetPassenger(val passenger: ActorRef)

case class GoToDetention()

case class LeaveSystem()

case class Line(id: Int, queue: ActorRef, bagggageScan: ActorRef, bodyScan: ActorRef, securityStation: ActorRef)

case class PlaceBaggage(val passenger: ActorRef, val baggage: Baggage)

case class ReturnBaggage(val baggage: Baggage)

case class ScanReport(val passenger: ActorRef, val scanResult: Boolean)

case class SecurityStationFinished()

case class SendPassengerToJail(val passenger: ActorRef)

case class SendPassengerToQueue(val passenger: ActorRef)

case class Shutdown()

case class ShutdownSecurityStation()

case class Startup()
