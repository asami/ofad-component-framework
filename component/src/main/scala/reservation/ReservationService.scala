package reservation

import scala.util.Try
import com.google.inject.Inject
import com.typesafe.config.Config
import reservation.datastore.IDataStore
import componentframework.ExecutionContext
import componentframework.event.Event
import componentframework.event.EventListener

class ReservationService @Inject()(
  val rule: ReservationService.ReservationRule,
  val datastore: IDataStore
) extends IReservationService, IReservationManagement, IReservationAdmin, IReservationMetrix, EventListener {
  // IReservationService
  def reserve(cmd: ReserveCommand)(using ctx: ExecutionContext): Try[ReserveResult] = ???
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
  def receive(evt: Event): Unit = Option(evt) collect {
    case m: ReservedEvent => reserved(m)
    case m: UnreservedEvent => unreserved(m)
  }

  def reserved(evt: ReservedEvent): Unit = ???
  def unreserved(evt: UnreservedEvent): Unit = ???
}

object ReservationService {
  case class ReservationRule(
  )
  object ReservationRule {
    def create(p: Config): ReservationRule = ???
  }
}
