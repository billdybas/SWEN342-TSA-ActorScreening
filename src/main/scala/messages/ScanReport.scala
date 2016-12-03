package messages

import actors.Passenger

case class ScanReport(val passenger: Passenger, val scanResult: Boolean)
