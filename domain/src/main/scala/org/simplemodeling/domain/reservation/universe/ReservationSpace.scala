package org.simplemodeling.domain.reservation.universe

import scala.util.Try
import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.datatype.{ResourceId, UserId}
import org.simplemodeling.componentframework.unitofwork.UnitOfWork.*
import org.simplemodeling.domain.reservation.*

case class ReservationSpace(
  reservations: Map[RoomId, ReservationSpace.ReservationSequence] = Map.empty
) {
  def isAvailable(roomid: RoomId, interval: Interval[LocalDateTime]): Boolean = ???
}

object ReservationSpace {
  val USER_NAME_LENGTH = 32
  val ROOM_NAME_LENGTH = 64

  case class UserName(name: String) {
    assert (name.length < USER_NAME_LENGTH)
  }
  case class RoomName(name: String) {
    assert (name.length < ROOM_NAME_LENGTH)
  }

  case class Reservation(
    id: ReservationId,
    interval: Interval[LocalDateTime],
    user: User,
    room: Room
  )

  case class User(
    id: UserId,
    name: UserName
  )

  case class Room(
    id: RoomId,
    name: RoomName
  )

  case class ReservationSequence(
    reservations: List[Reservation]
  )

  def build(
    reservations: List[entity.Reservation],
    users: List[entity.User],
    rooms: List[entity.Room]
  ): Try[ReservationSpace] = ???
}
