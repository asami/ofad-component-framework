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

class ReservationLogic_Try() extends IReservationLogic {
  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = {
    val uow = ctx.unitOfWork
    val roomid = RoomId(cmd.resourceId)
    for {
      space <- build_resrvation_space(roomid, cmd.interval)
      _ <- check_reservation(space, roomid, cmd.interval)
      r <- make_reservation(uow, roomid, cmd.interval, cmd.userId)
    } yield r
  }

  protected def build_resrvation_space(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): Try[ReservationSpace] =
    for {
      reservations <- fetch_reservations(roomid, interval)
      users <- fetch_users(reservations)
      rooms <- fetch_rooms(reservations)
      r <- ReservationSpace.build(reservations, users, rooms)
    } yield r

  protected def fetch_reservations(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): Try[List[entity.Reservation]] = {
    ???
  }

  protected def fetch_users(reservations: List[entity.Reservation]): Try[List[entity.User]] = {
    ???
  }

  protected def fetch_rooms(reservations: List[entity.Reservation]): Try[List[entity.Room]] = {
    ???
  }

  protected def check_reservation(space: ReservationSpace, roomid: RoomId, interval: Interval[LocalDateTime]): Try[Unit] =
    if (space.isAvailable(roomid, interval))
      Success(())
    else
      Failure(new IllegalArgumentException("Already reserved"))

  protected def make_reservation(
    uow: UnitOfWork,
    roomid: RoomId,
    interval: Interval[LocalDateTime],
    userid: UserId
  ): Try[ReserveResult] = {
    val data = Map(
      "room_id" -> roomid.id,
      "interval" -> interval,
      "user_id" -> userid.id
    )
    uow.create(ReservationStore, data).map(_ => ReserveResult())
  }

  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]] = ???
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]] = ???
}
