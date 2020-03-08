package org.interview

import org.interview.model._

object BasketPricer {

  def findMissingShopItems(basket: Basket, shop: Shop): List[ItemId] = {
    basket.items.keys.toList.filterNot(item => shop.itemExists(item))
  }

  def basketWithPrices(basket: Basket, shop: Shop): BasketWithPrices = {
    basket.items.toList.flatMap { case (itemId, itemQuantity) =>
      shop.itemPrice(itemId).map(itemPrice => itemId -> (itemQuantity, itemPrice))
    }.toMap
  }

  def priceBasketSubTotal(basketItemWithPrices: BasketWithPrices): BigDecimal = {
    val lineTotals = basketItemWithPrices.map { case (_, (itemQuantity, itemPrice)) => itemPrice * itemQuantity }
    lineTotals.sum
  }

  def calculateOffers(basketItemWithPrices: BasketWithPrices, offers: Map[DiscountName, OfferCalculator]): List[AppliedOffer] = {
    offers.toList.flatMap { case (discountName, offerCalculator) =>
      offerCalculator(basketItemWithPrices).map(discount => AppliedOffer(discountName, discount))
    }
  }

  def calculateTotalPrice(basketSubTotal: BigDecimal, offersApplied: List[AppliedOffer]): BigDecimal = {
    basketSubTotal - offersApplied.map(_.discountApplied).sum
  }


}
