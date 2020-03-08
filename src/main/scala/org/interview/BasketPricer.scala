package org.interview

import org.interview.model._

object BasketPricer {

  type OfferCalculator = List[(ItemId, ItemQuantity, ItemPrice)] => List[AppliedOffer]

  def findMissingShopItems(basket: Basket, shop: Shop): List[ItemId] = {
    basket.items.keys.toList.filterNot(item => shop.itemExists(item))
  }

  def basketWithPrices(basket: Basket, shop: Shop): List[(ItemId, ItemQuantity, ItemPrice)] = {
    basket.items.toList.flatMap { case (itemName, itemQuantity) =>
      shop.itemPrice(itemName).map(itemPrice => (itemName, itemQuantity, itemPrice))
    }
  }

  def priceBasketSubTotal(basketItemWithPrices: List[(ItemId, ItemQuantity, ItemPrice)]): BigDecimal = {
    val lineTotals = basketItemWithPrices.map { case (_, itemQuantity, itemPrice) => itemPrice * itemQuantity }
    lineTotals.sum
  }

  def calculateOffers(basketItemWithPrices: List[(ItemId, ItemQuantity, ItemPrice)], offers: List[OfferCalculator]): List[AppliedOffer] = {
    offers.flatMap(offerCalculator => offerCalculator(basketItemWithPrices))
  }

  def calculateTotalPrice(basketSubTotal: BigDecimal, offersApplied: List[AppliedOffer]): BigDecimal = {
    basketSubTotal - offersApplied.map(_.discountApplied).sum
  }


}
