package reservation

import scala.util.Try
import componentframework.ExecutionContext

trait IReservationService {
  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult]
  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult]
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]]
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]]
}
