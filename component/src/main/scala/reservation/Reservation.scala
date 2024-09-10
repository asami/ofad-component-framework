package reservation

import java.time.LocalDateTime
import spire.math.Interval

case class Reservation(
  id: ReservationId,
  resourceId: ResourceId,
  userId: UserId,
  interval: Interval[LocalDateTime]
) {
}
