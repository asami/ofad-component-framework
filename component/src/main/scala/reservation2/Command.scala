package reservation2

import scala.util.*
import java.time.LocalDateTime
import spire.math.Interval
import reservation.*

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
