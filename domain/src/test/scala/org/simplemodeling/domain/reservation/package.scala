package org.simplemodeling.domain

import cats.implicits._
import java.time.LocalDateTime

package object reservation {
  implicit def localDateTimeOrder: cats.Order[LocalDateTime] = new cats.Order[LocalDateTime] {
    def compare(x: LocalDateTime, y: LocalDateTime): Int = x.compareTo(y)
  }
}
