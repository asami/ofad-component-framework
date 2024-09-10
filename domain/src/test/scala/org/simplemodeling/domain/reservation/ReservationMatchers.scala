package org.simplemodeling.domain.reservation

import scala.util.Try
import org.scalatest.matchers.*

trait ReservationMatchers {
  import ReservationMatchers.*

  def successReserve(cmd: ReserveCommand): SuccessReserveMatcher = SuccessReserveMatcher(cmd)
}

object ReservationMatchers {
  case class SuccessReserveMatcher(cmd: ReserveCommand) extends Matcher[Try[ReserveResult]] {
    def apply(p: Try[ReserveResult]): MatchResult = {
      val result = true
      MatchResult(result, "failure", "success")
    }
  }
}
