package org.simplemodeling.domain.reservation2

import com.typesafe.config.Config
import io.circe.parser._
import io.circe.generic.auto._

case class ReservationConfig(
  cacheSize: Int
) {
  import ReservationConfig.*
}

object ReservationConfig {
  val PREFIX = "org.simplemodeling.domain.reservation"

  def create(config: Config): ReservationConfig = {
    val c = config.getConfig(PREFIX)
    val json = c.root().render()
    parse(json)
  }

  def parse(json: String): ReservationConfig = {
    val result = decode[ReservationConfig](json)
    ???
  }
}
