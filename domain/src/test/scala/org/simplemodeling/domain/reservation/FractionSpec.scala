package org.simplemodeling.domain.reservation

import org.junit.runner.RunWith
import org.scalatest.*, freespec.*
import org.scalatest.matchers.should.*
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class FractionSpec extends AnyFreeSpec
    with Matchers
    with GivenWhenThen
    with ScalaCheckDrivenPropertyChecks {

  case class Fraction(n: Int, d: Int) {
    require (d != 0)
    require (n != Integer.MIN_VALUE)
    require (d != Integer.MIN_VALUE)
    val numerator = n * (if (d < 0) -1 else 1)
    val denominator = d.abs
  }

  "分数(例題として)" - {
    "分子と分母の値域の全範囲でnumeratorメソッドとdenominatorメソッドを確認" in {
      forAll { (分子: Int, 分母: Int) =>
        whenever (分母 != 0 && 分母 != Integer.MIN_VALUE && 分子 != Integer.MIN_VALUE) {
          val 分数 = Fraction(分子, 分母)
          if (分子 < 0 && 分母 < 0 || 分子 > 0 && 分母 > 0)
            分数.numerator should be > 0
          else if (分子 != 0)
            分数.numerator should be < 0
          else
            分数.numerator should equal (0)
          分数.denominator should be > 0
        }
      }
    }
  }
}

