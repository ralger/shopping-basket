package org.interview

import org.interview.model._

trait Shop {
  def itemExists(name: String): Boolean
  def itemPrice(name: String): Option[ItemPrice]

  def basketPrices(basket: Basket): BasketWithPrices = {
    basket.items.toList.flatMap { case (itemId, itemQuantity) =>
      itemPrice(itemId).map(itemPrice => itemId -> (itemQuantity, itemPrice))
    }.toMap
  }

  def findBasketItemsNotStocked(basket: Basket): List[ItemId] = {
    basket.items.keys.toList.filterNot(item => itemExists(item))
  }
}
