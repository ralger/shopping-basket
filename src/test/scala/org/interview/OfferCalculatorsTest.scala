package org.interview

import org.interview.OfferCalculator
import org.interview.model._
import org.scalatest.FlatSpec
import org.scalatest.Matchers


class OfferCalculatorsTest extends FlatSpec with Matchers {

  val widgetTenPercentDiscountOffer: OfferCalculator = OfferCalculators.productPercentageDiscountOffer("Widget", 0.1)
  val widgetTenPercentDiscountMaxOneOffer: OfferCalculator = OfferCalculators.productPercentageDiscountOffer("Widget", 0.1, 1)

  val fixedOfferCalculator : OfferCalculator = _ => Some(BigDecimal(0.1))
  val fixedOfferWhenWidgetPuchased: OfferCalculator = OfferCalculators.conditionalOnQuantityOffer(1, "Widget", fixedOfferCalculator)

  "productPercentageDiscountOffer" should "return zero applied offers when the item doesn't exist" in {
    val basketItemsWithPrice = Map("NotAWidget" -> (3, BigDecimal(1)))
    val result = widgetTenPercentDiscountOffer(basketItemsWithPrice)
    result shouldBe None
  }

  it should "calculate the correct discount on the item" in {
    val basketItemsWithPrice = Map(
      "Widget" -> (2, BigDecimal(4)),
      "NotAWidget" -> (3, BigDecimal(1))
    )
    val result = widgetTenPercentDiscountOffer(basketItemsWithPrice)
    result shouldBe Some(BigDecimal(0.8))
  }

  it should "respect the offer maximum quantity when calculating the discount" in {
    val basketItemsWithPrice = Map("Widget" -> (2, BigDecimal(4)))
    val result = widgetTenPercentDiscountMaxOneOffer(basketItemsWithPrice)
    result shouldBe Some(BigDecimal(0.4))
  }

  it should "validate that discountPercentage is not negative" in {
    an [IllegalArgumentException] should be thrownBy OfferCalculators.productPercentageDiscountOffer("Widget", -0.1, 1)(Map())
  }

  it should "validate that discountPercentage is not more than 1" in {
    an [IllegalArgumentException] should be thrownBy OfferCalculators.productPercentageDiscountOffer("Widget", 2, 1)(Map())
  }

  it should "validate that discountMaxQuantity is not negative" in {
    an [IllegalArgumentException] should be thrownBy OfferCalculators.productPercentageDiscountOffer("Widget", 0.1, -1)(Map())
  }

  "conditionalOnQuantityOffer" should "return zero applied offers when the item doesn't exist" in {
    val basketItemsWithPrice = Map("NotAWidget" -> (3, BigDecimal(1)))
    val result = fixedOfferWhenWidgetPuchased(basketItemsWithPrice)
    result shouldBe None
  }

  it should "return the provided offer when the conditional Item and Quantity is met" in {
    val basketItemsWithPrice = Map("Widget" -> (3, BigDecimal(1)))
    val result = fixedOfferWhenWidgetPuchased(basketItemsWithPrice)
    result shouldBe Some(BigDecimal(0.1))
  }

  it should "validate that conditionalQuantity is not negative" in {
    an [IllegalArgumentException] should be thrownBy OfferCalculators.conditionalOnQuantityOffer(-2, "Widget", fixedOfferCalculator)(Map())
  }


}
