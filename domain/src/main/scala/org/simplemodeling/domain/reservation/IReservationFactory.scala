package org.simplemodeling.domain.reservation

import scala.util.Try

trait IReservationFactory {
  def createReservation(p: Map[String, Any]): Try[Reservation]
}
