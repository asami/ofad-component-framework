package org.simplemodeling.domain.reservation

import cats._
import cats.data._
import cats.free._
import cats.implicits._
import scala.util.{Try, Success, Failure}
import org.simplemodeling.componentframework.ExecutionContext
import org.simplemodeling.componentframework.entity.*
import org.simplemodeling.componentframework.entity.EntityStore
import org.simplemodeling.componentframework.entity.EntityStore.EntityInstance
import org.simplemodeling.componentframework.entity.EntityStore.Record
import org.simplemodeling.componentframework.unitofwork.UnitOfWork.*
import org.simplemodeling.domain.reservation.universe.ReservationSpace

class ReservationLogic() extends IReservationLogic {
  given EntityInstance[Product] with {
  }

  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = {
    val interpreter: UnitOfWorkOp ~> Id = ctx.unitOfWorkInterpreter
    val a: EitherT[UnitOfWorkFM, Throwable, ReserveResult] = reserveFM(cmd)
    val b: UnitOfWorkFM[Either[Throwable, ReserveResult]] = a.value
    val c: Either[Throwable, ReserveResult] = b.foldMap(interpreter)
    c.toTry
  }

  def reserveFM(cmd: ReserveCommand)(using ctx: ExecutionContext): UnitOfWorkEitherFM[ReserveResult] = {
    val store: EntityStore = ???
    val data: Record = Map.empty

//     val program: UnitOfWorkEitherFM[ReserveResult] = for {
//       u <- make_reservation_space2()
// //      _ <- create(store, data)
//       r <- make_reserve2(u)
//     } yield r
//     // val z: Either[Throwable, ReserveResult] = program.value.foldMap(y)
//     // Free.pure(z)
//     program
    for {
      u <- make_reservation_space2()
//      _ <- create(store, data)
      r <- make_reserve2(u)
    } yield r
  }

  import cats.arrow.FunctionK

  def x: UnitOfWorkOp ~> Try = ???

//  type X[T] = EitherT[UnitOfWorkEitherFMThrowable, T]

  def y: UnitOfWorkOp ~> Id = ???

  protected def make_reservation_space2(): UnitOfWorkEitherFM[ReservationSpace] = ???

  protected def make_reserve2(space: ReservationSpace): UnitOfWorkEitherFM[ReserveResult] = ???

  protected def make_reservation_space3(): UnitOfWorkFM[ReservationSpace] = ???

  protected def make_reserve3(space: ReservationSpace): UnitOfWorkFM[ReserveResult] = ???

  def reserveX(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = {
    val store: EntityStore = ???
    val data: Record = Map.empty

    val program = for {
      u <- make_reservation_space()
//      _ <- create(store, data)
      r <- u match {
        case Success(s) => make_reserve(s)
        case Failure(e) => Free.pure(Failure(e))
      }
    } yield r
    program.foldMap(ctx.unitOfWorkInterpreter)
  }

  protected def make_reservation_space(): UnitOfWorkFM[Try[ReservationSpace]] = ???

  protected def make_reserve(space: ReservationSpace): UnitOfWorkFM[Try[ReserveResult]] = ???

  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]] = ???
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]] = ???
}

object ReservationLogic {
  def create(config: ReservationConfig): ReservationLogic = new ReservationLogic()
}
