package app

import javax.inject.Singleton
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import org.simplemodeling.domain.reservation.*

class AppModule(config: Config) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[IReservationService]).to(classOf[ReservationService]).in(classOf[Singleton])
    install(new ReservationServiceModule(config))
  }
}
