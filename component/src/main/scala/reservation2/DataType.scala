package reservation2

import scala.util.*
import reservation.*

case class ReservationId(id: String) {
  require (ReservationId.validate(id), s"Bad id: $id")
}

object ReservationId {
  def validate(id: String): Boolean = ???

  def create(id: String): Try[ReservationId] =
    if (validate(id))
      Success(ReservationId(id))
    else
      Failure(new IllegalArgumentException(s"Bad id: $id"))
}
