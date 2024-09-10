package org.simplemodeling.domain.reservation

import scala.util.Try
import com.google.inject.Inject
import org.simplemodeling.componentframework.ExecutionContext
import org.simplemodeling.componentframework.event.Event
import org.simplemodeling.componentframework.event.EventListener
import org.simplemodeling.domain.reservation.repository.IReservationRepository

class ReservationService @Inject()(
  val config: ReservationConfig,
  val rule: IReservationRule,
  val logic: IReservationLogic,
  val factory: IReservationFactory,
  val repository: IReservationRepository,
  val receptor: IReservationReceptor
) extends IReservationService, IReservationManagement, IReservationAdmin, IReservationMetrix, EventListener {
  // IReservationService
  protected def invariant: Try[Unit] = Try{}

  protected def reserve_precondition(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[Unit] = Try{}

  protected def reserve_postcondition(cmd: ReserveCommand, result: ReserveResult)(using ctx: ExecutionContext): Try[Unit] = Try{}

  protected def do_reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] =
    logic.reserve(cmd)

  def unreserve(cmd: UnreserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
  def getReservation(id: ReservationId)(using ctx: ExecutionContext): Try[Option[Reservation]] = ???
  def queryReservation(q: ReservationQuery)(using ctx: ExecutionContext): Try[Vector[Reservation]] = ???

  // IReservationManagement

  // IReservationAdmin

  // IReservationMetrix
  def numberOfCacheHit: Int = ???
  def numberOfCacheMiss: Int = ???
  def numberOfOperationCalls: Int = ???

  // EventListener
  def receive(evt: Event): Unit = receptor.receive(evt)
}

object ReservationService {
  def createForTest(): ReservationService = {
    val config = ReservationConfig.createForTest()
    create(config)
  }

  def create(config: ReservationConfig): ReservationService = {
    import org.simplemodeling.domain.reservation.repository.ReservationRepository

    val rule = ReservationRule.create(config)
    val logic = ReservationLogic.create(config)
    val factory = ReservationFactory.create(config)
    val repository = ReservationRepository.create(config)
    val receptor = ReservationReceptor.create(config)
    new ReservationService(config, rule, logic, factory, repository, receptor)
  }
}
