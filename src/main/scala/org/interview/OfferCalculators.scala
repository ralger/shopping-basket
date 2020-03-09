package org.interview

import org.interview.OfferCalculator
import org.interview.model._

object OfferCalculators {

  def productPercentageDiscountOffer(discountProductName: ItemId,
                                     discountPercentage : Double)(basketProducts: BasketWithPrices): Option[BigDecimal] = {
    productPercentageDiscountOffer(discountProductName, discountPercentage, Int.MaxValue)(basketProducts)
  }

  def productPercentageDiscountOffer(discountProductName: ItemId,
                                     discountPercentage : Double,
                                     discountMaxQauntity: Int)(basketProducts: BasketWithPrices): Option[BigDecimal] = {
    if (discountPercentage < 0) throw new IllegalArgumentException("DiscountPercentage can not be negative")
    if (discountPercentage > 1) throw new IllegalArgumentException("DiscountPercentage can not be greater than 1")
    if (discountMaxQauntity < 0) throw new IllegalArgumentException("DiscountPercentage can not be negative")

    basketProducts.get(discountProductName) map { case (itemQuantity, itemPrice) =>
      itemPrice * Math.min(itemQuantity, discountMaxQauntity) * discountPercentage
    }
  }

  def conditionalOnQuantityOffer(conditionalQuantity: Int,
                                 conditionalProduct: ItemId,
                                 conditionalOffer: OfferCalculator)(basketProducts: BasketWithPrices): Option[BigDecimal] = {
    if (conditionalQuantity <= 0)  throw new IllegalArgumentException("ConditionalQuantity should be greater than 0")

    basketProducts.get(conditionalProduct) flatMap { case (itemQuantity, _) =>
      val conditionalOfferResult = if (itemQuantity >= conditionalQuantity)
        Some(conditionalOffer(basketProducts))
      else
        None
      conditionalOfferResult.flatten
    }


  }


}
