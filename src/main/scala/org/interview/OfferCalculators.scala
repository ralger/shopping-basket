package org.interview

import org.interview.OfferCalculator
import org.interview.model._

object OfferCalculators {

  def productPercentageDiscountOffer(discountProductName: ItemId,
                                     discountPercentage : Double)(basketProducts: BasketWithPrices): List[AppliedOffer] = {
    basketProducts.get(discountProductName) match {
      case Some((itemQuantity, itemPrice)) => {
        val discount = itemPrice * itemQuantity * discountPercentage
        AppliedOffer(f"$discountProductName ${discountPercentage * 100}%2.0f%% off", discount) :: Nil
      }
      case None => List()
    }
  }

  def conditionalOnQuantityOffer(conditionalQuantity: Int,
                                 conditionalProduct: ItemId,
                                 conditionalOffer: OfferCalculator)(basketProducts: BasketWithPrices): List[AppliedOffer] = {
    val conditionalOfferApplied = basketProducts.get(conditionalProduct) match {
      case Some((itemQuantity, _)) => {
        if (itemQuantity >= conditionalQuantity)
          conditionalOffer(basketProducts)
        else
          List()
      }
      case None => List()
    }
    conditionalOfferApplied.map(offer => AppliedOffer(s"${offer.discountName} when buying $conditionalQuantity $conditionalProduct", offer.discountApplied))
  }


}
