package org.simplemodeling.domain.reservation

import com.typesafe.config.Config
import io.circe.parser._
import io.circe.generic.auto._

case class ReservationConfig(
  cacheSize: Int = 10000
) {
  import ReservationConfig.*
}

object ReservationConfig {
  val PREFIX = "org.simplemodeling.domain.reservation"

  val default = ReservationConfig()

  def create(config: Config): ReservationConfig = {
    val c = config.getConfig(PREFIX)
    val json = c.root().render()
    parse(json)
  }

  def parse(json: String): ReservationConfig = {
    decode[ReservationConfig](json)
  }

  def createForTest(): ReservationConfig = default
}

// class ReservationConfig(config: Config) {
//   import ReservationConfig.*

//   val cacheSize = config.getInt(CACHE_SIZE)
// }

// object ReservationConfig {
//   val CACHE_SIZE = "org.simplemodeling.domain.reservation.cache.size"

//   def create(config: Config): ReservationConfig = new ReservationConfig(config)
// }
