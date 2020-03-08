package org.interview

import org.interview.OfferCalculator
import org.interview.model._

object OfferCalculators {

  def productPercentageDiscountOffer(discountProductName: ItemId,
                                     discountPercentage : Double)(basketProducts: BasketWithPrices): List[AppliedOffer] = {
    val discountedProducts = basketProducts.filter { case (itemName, (_, _)) => itemName == discountProductName }
    if (discountedProducts.nonEmpty) {
      val discount = discountedProducts.map { case (_, (itemQuantity, itemPrice)) => itemPrice * itemQuantity * discountPercentage }.sum
      AppliedOffer(f"$discountProductName ${discountPercentage * 100}%2.0f%% off", discount) :: Nil
    } else
      List()
  }

  def conditionalOnQuantityOffer(conditionalQuantity: Int,
                                 conditionalProduct: ItemId,
                                 conditionalOffer: OfferCalculator)(basketProducts: BasketWithPrices): List[AppliedOffer] = {
    val conditionalOfferApplied = if (basketProducts.exists { case (itemName, (itemQuantity, _)) => itemName == conditionalProduct && itemQuantity >= conditionalQuantity })
      conditionalOffer(basketProducts)
    else
      List()
    conditionalOfferApplied.map(offer => AppliedOffer(s"${offer.discountName} when buying $conditionalQuantity $conditionalProduct", offer.discountApplied))
  }


}
