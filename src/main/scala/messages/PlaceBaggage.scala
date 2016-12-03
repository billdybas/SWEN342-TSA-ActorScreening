package messages

import akka.actor.ActorRef

import actors.Baggage

case class PlaceBaggage(val passenger: ActorRef, val baggage: Baggage)
