package org.simplemodeling.domain

import org.simplemodeling.componentframework.entity.EntityStore
import org.simplemodeling.componentframework.entity.EntityStore.EntityInstance

package object reservation {
  given EntityInstance[entity.Reservation] with {
  }

  val ReservationStore = EntityStore("reservation")
}
