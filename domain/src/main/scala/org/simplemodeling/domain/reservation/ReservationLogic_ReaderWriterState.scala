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
import org.simplemodeling.componentframework.unitofwork.UnitOfWork.*
import org.simplemodeling.domain.reservation.universe.ReservationSpace

class ReservationLogic_ReaderWriterState() extends IReservationLogic {
  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = {
    val interpreter: UnitOfWorkOp ~> Id = ctx.unitOfWorkInterpreter
    reserveFM(cmd).value.foldMap(interpreter).toTry
  }

  def reserve2(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = {
    val interpreter: UnitOfWorkOp ~> Id = ctx.unitOfWorkInterpreter
    val a: EitherT[UnitOfWorkFM, Throwable, ReserveResult] = reserveFM(cmd)
    val b: UnitOfWorkFM[Either[Throwable, ReserveResult]] = a.value
    val c: Either[Throwable, ReserveResult] = b.foldMap(interpreter)
    c.toTry
  }

  def reserveFM(cmd: ReserveCommand)(using ctx: ExecutionContext): UnitOfWorkEitherFM[ReserveResult] = {
    reserveRSW(cmd).run(ctx, Map.empty).map(_._3)
  }

  def reserveFM2(cmd: ReserveCommand)(using ctx: ExecutionContext): UnitOfWorkEitherFM[ReserveResult] = {
    val a: UnitOfWorkRWSEitherFM[ReserveResult] = reserveRSW(cmd)
    val b: UnitOfWorkEitherFM[(Log, Map[String, Any], ReserveResult)] = a.run(ctx, Map.empty)
    b.map(_._3)
  }

  def reserveRSW(cmd: ReserveCommand): UnitOfWorkRWSEitherFM[ReserveResult] = {
    val roomid = RoomId(cmd.resourceId)
    for {
      space <- build_reservation_space(RoomId(cmd.resourceId), cmd.interval)
      _ <- check_reservation(space, roomid, cmd.interval)
      r <- make_reservation(roomid, cmd.interval, cmd.userId)
    } yield r
  }

  protected def build_reservation_space(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): UnitOfWorkRWSEitherFM[ReservationSpace] =
    RWST { (ctx, state) =>
      for {
        reservations <- fetch_reservations(roomid, interval)
        users <- fetch_users(reservations)
        rooms <- fetch_rooms(reservations)
        r <- EitherT(Free.pure(ReservationSpace.build(reservations, users, rooms).toEither))
      } yield (Nil, state, r)
    }

  protected def check_reservation(space: ReservationSpace, roomid: RoomId, interval: Interval[LocalDateTime]): UnitOfWorkRWSEitherFM[Unit] =
    RWST { (ctx, state) =>
      EitherT {
        Free.pure {
          if (space.isAvailable(roomid, interval))
            Right((Nil, state, ()))
          else
            Left(new IllegalArgumentException("Already reserved"))
        }
      }
    }

  protected def fetch_reservations(
    roomid: RoomId,
    interval: Interval[LocalDateTime]
  ): UnitOfWorkEitherFM[List[entity.Reservation]] = {
    ???
  }

  protected def fetch_users(reservations: List[entity.Reservation]): UnitOfWorkEitherFM[List[entity.User]] = {
    ???
  }

  protected def fetch_rooms(reservations: List[entity.Reservation]): UnitOfWorkEitherFM[List[entity.Room]] = {
    ???
  }

  protected def make_reservation(
    roomid: RoomId,
    interval: Interval[LocalDateTime],
    userid: UserId
  ): UnitOfWorkRWSEitherFM[ReserveResult] = {
    val data = Map(
      "room_id" -> roomid.id,
      "interval" -> interval,
      "user_id" -> userid.id
    )
    RWST { (config, state) =>
      EitherT {
        UnitOfWork.create(ReservationStore, data).map(_.map(_ => (Nil, state, ReserveResult())).toEither)
      }
    }
  }

  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]] = ???
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]] = ???
}
