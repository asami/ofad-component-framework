package org.simplemodeling.domain.reservation

import scala.util.Try
import org.simplemodeling.componentframework.datatype.Money

class ReservationRule() extends IReservationRule {
  def calcReservationFee(reservation: Reservation): Try[Money] = ???
}

object ReservationRule {
  def create(config: ReservationConfig): ReservationRule = new ReservationRule()
}
