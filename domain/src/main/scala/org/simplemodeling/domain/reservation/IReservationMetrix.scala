package org.simplemodeling.domain.reservation

trait IReservationMetrix {
  def numberOfOperationCalls: Int
  def numberOfCacheHit: Int
  def numberOfCacheMiss: Int
}
