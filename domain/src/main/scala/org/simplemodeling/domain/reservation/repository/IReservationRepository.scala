package org.simplemodeling.domain.reservation.repository

import scala.util.Try
import java.time.LocalDateTime
import spire.math.Interval
import org.simplemodeling.componentframework.datatype.UserId
import org.simplemodeling.componentframework.datatype.ResourceId
import org.simplemodeling.componentframework.entity.ListDirective
import org.simplemodeling.componentframework.entity.ListResult
import org.simplemodeling.componentframework.entity.User
import org.simplemodeling.domain.reservation.ReservationConfig
import org.simplemodeling.domain.reservation.ReservationId

trait IReservationRepository {
  import IReservationRepository._

  def get(id: ReservationId): Try[Option[Reservation]]
  def list(query: Query, directive: ListDirective): Try[ListResult[Reservation]]

  def create(entity: datastore.create.Reservation): Try[Unit]
  def create(record: Map[String, Any]): Try[Unit]
  def update(directive: datastore.update.Reservation): Try[Unit]
  def update(id: ReservationId, record: Map[String, Any]): Try[Unit]
  def delete(id: ReservationId): Try[Unit]
}

object IReservationRepository {
  case class Reservation(
    id: ReservationId,
    resource: Resource,
    user: User,
    interval: Interval[LocalDateTime]
  )

  case class Resource(
    id: ResourceId,
    name: String
  )

  case class Query(
    resourceId: Option[ResourceId],
    userId: Option[UserId],
    interval: Option[Interval[LocalDateTime]]
  )

  object datastore {
    case class Reservation(
      resourceId: ResourceId,
      userId: UserId,
      interval: Interval[LocalDateTime]
    )

    object create {
      case class Reservation(
        resourceId: ResourceId,
        userId: UserId,
        interval: Interval[LocalDateTime]
      )
    }

    object update {
      case class Reservation(
        resourceId: Option[ResourceId],
        userId: Option[UserId],
        interval: Option[Interval[LocalDateTime]]
      )
    }
  }
}
