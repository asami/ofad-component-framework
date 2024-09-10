package reservation

import java.time.LocalDateTime
import spire.math.Interval

case class ReservationQuery(
  resourceId: Option[ResourceId],
  userId: Option[UserId],
  interval: Option[Interval[LocalDateTime]]
) {
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
