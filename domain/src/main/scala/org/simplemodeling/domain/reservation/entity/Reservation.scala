package org.simplemodeling.domain.reservation.entity

import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.datatype.{ResourceId, UserId}
import org.simplemodeling.domain.reservation.*

case class Reservation(
  id: ReservationId,
  resourceId: ResourceId,
  userId: UserId,
  interval: Interval[LocalDateTime]
) {
}
