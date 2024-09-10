package org.simplemodeling.domain.reservation

import scala.util.Try
import org.simplemodeling.componentframework.ExecutionContext

trait IReservationService {
  protected def invariant: Try[Unit]

  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] =
    for {
      _ <- invariant
      _ <- reserve_precondition(cmd)
      r <- do_reserve(cmd)
      _ <- reserve_postcondition(cmd, r)
      _ <- invariant
    } yield r

  protected def reserve_precondition(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[Unit]
  protected def reserve_postcondition(cmd: ReserveCommand, result: ReserveResult)(using ctx: ExecutionContext): Try[Unit]

  protected def do_reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult]
}
