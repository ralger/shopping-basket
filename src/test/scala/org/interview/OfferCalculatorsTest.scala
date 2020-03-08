package org.interview

import org.interview.OfferCalculator
import org.interview.model._
import org.scalatest.FlatSpec
import org.scalatest.Matchers


class OfferCalculatorsTest extends FlatSpec with Matchers {

  val widgetTenPercentDiscountOffer: OfferCalculator = OfferCalculators.productPercentageDiscountOffer("Widget", 0.1)

  val fixedOfferCalculator : OfferCalculator = _ => List(AppliedOffer("Fixed Amount offer", 0.1))
  val fixedOfferWhenWidgetPuchased: OfferCalculator = OfferCalculators.conditionalOnQuantityOffer(1, "Widget", fixedOfferCalculator)

  "productPercentageDiscountOffer" should "return zero applied offers when the item doesn't exist" in {
    val basketItemsWithPrice = Map("NotAWidget" -> (3, BigDecimal(1)))
    val result = widgetTenPercentDiscountOffer(basketItemsWithPrice)
    result.length shouldBe 0
  }

  it should "calculate the correct discount on the item" in {
    val basketItemsWithPrice = Map(
      "Widget" -> (2, BigDecimal(4)),
      "NotAWidget" -> (3, BigDecimal(1))
    )
    val result = widgetTenPercentDiscountOffer(basketItemsWithPrice)
    result.length shouldBe 1
    result.head.discountApplied shouldBe BigDecimal(0.8)
  }

  "conditionalOnQuantityOffer" should "return zero applied offers when the item doesn't exist" in {
    val basketItemsWithPrice = Map("NotAWidget" -> (3, BigDecimal(1)))
    val result = fixedOfferWhenWidgetPuchased(basketItemsWithPrice)
    result.length shouldBe 0
  }

  it should "return the provided offer when the conditional Item and Quantity is met" in {
    val basketItemsWithPrice = Map("Widget" -> (3, BigDecimal(1)))
    val result = fixedOfferWhenWidgetPuchased(basketItemsWithPrice)
    result.length shouldBe 1
    result.head.discountApplied shouldBe BigDecimal(0.1)
  }


}
