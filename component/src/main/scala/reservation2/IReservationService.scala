package reservation2

import scala.util.Try
import componentframework.ExecutionContext
import reservation.*

trait IReservationService {
  protected def invariant: Try[Unit]

  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] =
    for {
      _ <- reserve_precondition(cmd)
      r <- do_reserve(cmd)
      _ <- reserve_postcondition(cmd, r)
    } yield r

  protected def reserve_precondition(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[Unit]
  protected def reserve_postcondition(cmd: ReserveCommand, result: ReserveResult)(using ctx: ExecutionContext): Try[Unit]

  protected def do_reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult]
}
