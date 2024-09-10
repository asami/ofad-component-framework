package org.simplemodeling.domain.reservation

import org.junit.runner.RunWith
import org.scalatest.*, freespec.*
import org.scalatest.matchers.should.*
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import cats.implicits._
import java.time.LocalDateTime
import spire.math.Interval
import spire.math.interval._
import org.simplemodeling.componentframework.ExecutionContext
import org.simplemodeling.componentframework.datatype.{UserId, ResourceId}

class ReservationServiceSpec extends AnyFreeSpec
    with Matchers
    with GivenWhenThen
    with ScalaCheckDrivenPropertyChecks
    with ReservationMatchers {

  "ReservationService" - {
    "reserveメソッド" - {
      "予約の追加" - {
        "現在時刻より後ろの有効な予約時間を予約" in {
          Given("テスト用に作成したReservationServiceを使用")
          val service = ReservationService.createForTest()

          When("運用時間内の時間")
          val dt = "2024-01-15T10:00:00+JST"

          Then("テスト対象時刻で実行コンテキストを用意")
          given ExecutionContext = ExecutionContext.createWithCurrentDateTime(dt)

          And("現在時刻より後ろの有効な予約時間で予約")
          val rid = ResourceId("id1234")
          val uid = UserId("user789")
          val start = LocalDateTime.of(2024, 1, 15, 11, 00)
          val end = LocalDateTime.of(2024, 1, 15, 12, 00)
          val interval = Interval.openUpper(start, end)
          val cmd = ReserveCommand(rid, uid, interval)

          service.reserve(cmd) should successReserve(cmd)
        }
      }
    }
  }
}
