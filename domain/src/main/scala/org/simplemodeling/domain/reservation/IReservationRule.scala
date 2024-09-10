package org.simplemodeling.domain.reservation

import scala.util.Try
import org.simplemodeling.componentframework.datatype.Money

trait IReservationRule {
  def calcReservationFee(reservation: Reservation): Try[Money]
}
