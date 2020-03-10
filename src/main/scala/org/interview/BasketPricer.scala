package org.interview

import org.interview.model._

trait BasketPricer {

  val offers: Map[DiscountName, OfferCalculator]

  def priceBasketSubTotal(basketItemWithPrices: BasketWithPrices): BigDecimal = {
    validateItemQuantity(basketItemWithPrices)
    val lineTotals = basketItemWithPrices.map { case (_, (itemQuantity, itemPrice)) => itemPrice * itemQuantity }
    lineTotals.sum
  }

  def calculateOffers(basketItemWithPrices: BasketWithPrices): List[AppliedOffer] = {
    validateItemQuantity(basketItemWithPrices)
    offers.toList.flatMap { case (discountName, offerCalculator) =>
      offerCalculator(basketItemWithPrices).map(discount => AppliedOffer(discountName, discount))
    }.filter(appliedOffer => appliedOffer.discountApplied >= BigDecimal(0))
  }

  def calculateTotalPrice(basketSubTotal: BigDecimal, offersApplied: List[AppliedOffer]): BigDecimal = {
    val total = basketSubTotal - offersApplied.map(_.discountApplied).sum
    if (total >= BigDecimal(0)) total else BigDecimal(0)
  }

  private def validateItemQuantity(basketItemWithPrices: BasketWithPrices) = {
    if (basketItemWithPrices.exists { case (_, (itemQuantity, _)) => itemQuantity < 1 }) throw new IllegalArgumentException("Basket provided has items with less than 1 quantity")
  }


}
