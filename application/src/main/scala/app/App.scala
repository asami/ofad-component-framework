package app

import javax.inject.Inject
import com.google.inject.Guice
import com.typesafe.config.ConfigFactory
import org.simplemodeling.domain.reservation.IReservationService

class App @Inject()(reservation: IReservationService) {
  def run(): Unit = ???
}

object App {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val injector = Guice.createInjector(new AppModule(config))
    val app = injector.getInstance(classOf[App])
    app.run()
  }
}
