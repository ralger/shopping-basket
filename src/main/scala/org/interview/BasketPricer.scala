package org.interview

import org.interview.model._

trait BasketPricer {

  val offers: Map[DiscountName, OfferCalculator]

  def priceBasketSubTotal(basketItemWithPrices: BasketWithPrices): BigDecimal = {
    val lineTotals = basketItemWithPrices.map { case (_, (itemQuantity, itemPrice)) => itemPrice * itemQuantity }
    lineTotals.sum
  }

  def calculateOffers(basketItemWithPrices: BasketWithPrices): List[AppliedOffer] = {
    offers.toList.flatMap { case (discountName, offerCalculator) =>
      offerCalculator(basketItemWithPrices).map(discount => AppliedOffer(discountName, discount))
    }
  }

  def calculateTotalPrice(basketSubTotal: BigDecimal, offersApplied: List[AppliedOffer]): BigDecimal = {
    val total = basketSubTotal - offersApplied.map(_.discountApplied).sum
    if (total >= BigDecimal(0)) total else BigDecimal(0)
  }


}
