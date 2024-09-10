package org.simplemodeling.domain.reservation

import scala.util.Try

class ReservationFactory(config: ReservationConfig) extends IReservationFactory {
  def createReservation(p: Map[String, Any]): Try[Reservation] = ???
}

object ReservationFactory {
  def create(config: ReservationConfig): ReservationFactory = new ReservationFactory(config)
}
