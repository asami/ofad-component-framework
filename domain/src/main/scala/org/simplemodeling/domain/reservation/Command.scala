package org.simplemodeling.domain.reservation

import scala.util.{Try, Success, Failure}
import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.datatype.{ResourceId, UserId}

case class ReservationQuery(
  resourceId: Option[ResourceId],
  userId: Option[UserId],
  interval: Option[Interval[LocalDateTime]]
) {
  require (ReservationQuery.validate(resourceId, userId, interval), s"Bad query: $this")
}

object ReservationQuery {
  def validate(
    resourceId: Option[ResourceId],
    userId: Option[UserId],
    interval: Option[Interval[LocalDateTime]]
  ): Boolean = ???

  def create(
    resourceId: Option[ResourceId],
    userId: Option[UserId],
    interval: Option[Interval[LocalDateTime]]
  ): Try[ReservationQuery] =
    if (validate(resourceId, userId, interval))
      Success(ReservationQuery(resourceId, userId, interval))
    else
      Failure(new IllegalArgumentException(s"Bad query: $resourceId, $userId, $interval"))
}

case class ReserveCommand(
  resourceId: ResourceId,
  userId: UserId,
  interval: Interval[LocalDateTime]
) {
}

case class UnreserveCommand(
  id: ReservationId
) {
}

case class ReserveResult(
) {
}

case class UnreserveResult(
) {
}
