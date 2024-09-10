package org.simplemodeling.domain.reservation

import org.simplemodeling.componentframework.event.Event

trait ReservationEvent extends Event {
}

case class ReservedEvent(
  id: ReservationId
) extends ReservationEvent {
}

case class UnreservedEvent(
  id: ReservationId
) extends ReservationEvent {
}
