package reservation

import javax.inject.Singleton
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import reservation.ReservationService.ReservationRule
import reservation.datastore.IDataStore
import reservation.datastore.DataStore

class ReservationServiceModule(config: Config) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[IDataStore]).to(classOf[DataStore]).in(classOf[Singleton])
    bind(classOf[ReservationRule]).toInstance(ReservationRule.create(config))
  }
}
