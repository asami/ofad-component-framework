package org.simplemodeling.domain.reservation

import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.datatype.{ResourceId, UserId}

case class Reservation(
  id: ReservationId,
  resourceId: ResourceId,
  userId: UserId,
  interval: Interval[LocalDateTime]
) {
}
