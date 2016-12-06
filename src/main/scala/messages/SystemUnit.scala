package messages

import actors.{PassengerQueue, BaggageScan, BodyScan, SecurityStation}

case class SystemUnit(queue: PassengerQueue, bagScan: BaggageScan, bodyScan: BodyScan, securityStation: SecurityStation)
