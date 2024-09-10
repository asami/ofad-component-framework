package org.simplemodeling.domain.reservation

import javax.inject.Singleton
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import org.simplemodeling.domain.reservation.repository.IReservationRepository
import org.simplemodeling.domain.reservation.repository.ReservationRepository

class ReservationServiceModule(config: Config) extends AbstractModule {
  override def configure(): Unit = {
    val c = ReservationConfig.create(config)
    bind(classOf[IReservationRule]).toInstance(ReservationRule.create(c))
    bind(classOf[IReservationLogic]).toInstance(ReservationLogic.create(c))
    bind(classOf[IReservationFactory]).toInstance(ReservationFactory.create(c))
    bind(classOf[IReservationRepository]).toInstance(ReservationRepository.create(c))
    bind(classOf[IReservationReceptor]).toInstance(ReservationReceptor.create(c))
  }
}
