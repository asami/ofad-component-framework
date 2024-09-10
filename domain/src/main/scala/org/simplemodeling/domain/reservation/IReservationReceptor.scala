package org.simplemodeling.domain.reservation

import org.simplemodeling.componentframework.event.Event

trait IReservationReceptor {
  def receive(evt: Event): Unit
}
