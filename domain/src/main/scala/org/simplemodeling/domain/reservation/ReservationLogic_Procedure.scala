package org.simplemodeling.domain.reservation

import cats._
import cats.data._
import cats.free._
import cats.implicits._
import scala.util.{Try, Success, Failure}
import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.ExecutionContext
import org.simplemodeling.componentframework.datatype.UserId
import org.simplemodeling.componentframework.entity.*
import org.simplemodeling.componentframework.entity.EntityStore
import org.simplemodeling.componentframework.entity.EntityStore.EntityInstance
import org.simplemodeling.componentframework.entity.EntityStore.Record
import org.simplemodeling.componentframework.unitofwork.UnitOfWork
import org.simplemodeling.domain.reservation.universe.ReservationSpace

class ReservationLogic_Procedure() extends IReservationLogic {
  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = Try {
    val uow = ctx.unitOfWork
    val roomid = RoomId(cmd.resourceId)
    val space = build_resrvation_space(roomid, cmd.interval)
    if (space.isAvailable(roomid, cmd.interval))
      make_reservation(uow, roomid, cmd.interval, cmd.userId)
    else
      throw new IllegalStateException("???")
  }

  protected def build_resrvation_space(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): ReservationSpace = {
    val reservations = fetch_reservations(roomid, interval)
    val users = fetch_users(reservations)
    val rooms = fetch_rooms(reservations)
    ReservationSpace.build(reservations, users, rooms).get
  }

  protected def fetch_reservations(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): List[entity.Reservation] = {
    ???
  }

  protected def fetch_users(reservations: List[entity.Reservation]): List[entity.User] = {
    ???
  }

  protected def fetch_rooms(reservations: List[entity.Reservation]): List[entity.Room] = {
    ???
  }

  protected def make_reservation(
    uow: UnitOfWork,
    roomid: RoomId,
    interval: Interval[LocalDateTime],
    userid: UserId
  ): ReserveResult = {
    val data = Map(
      "room_id" -> roomid.id,
      "interval" -> interval,
      "user_id" -> userid.id
    )
    uow.create(ReservationStore, data) match {
      case Success(s) => ReserveResult()
      case Failure(e) => throw e
    }
  }

  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]] = ???
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]] = ???
}
