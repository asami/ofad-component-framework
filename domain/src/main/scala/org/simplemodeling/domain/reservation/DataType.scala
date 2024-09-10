package org.simplemodeling.domain.reservation

import scala.util.*
import org.simplemodeling.componentframework.datatype.ResourceId

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

case class RoomId(id: String)

object RoomId {
  def apply(id: ResourceId): RoomId = RoomId(id.id)
}
