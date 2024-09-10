package org.simplemodeling.domain.reservation

import org.simplemodeling.componentframework.event.Event

class ReservationReceptor(config: ReservationConfig) extends IReservationReceptor {
  def receive(evt: Event): Unit = Option(evt) collect {
    case m: ReservedEvent => reserved(m)
    case m: UnreservedEvent => unreserved(m)
  }

  def reserved(evt: ReservedEvent): Unit = ???
  def unreserved(evt: UnreservedEvent): Unit = ???
}

object ReservationReceptor {
  def create(config: ReservationConfig): ReservationReceptor = new ReservationReceptor(config)
}
