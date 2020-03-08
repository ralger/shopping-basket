package org.interview.simpleshop

import org.interview.OfferCalculator
import org.interview.Shop
import org.interview.model.ItemId

/*
  A very simple Shop implementation where items are stored locally in a Map
 */
object ExampleShop extends Shop {

  private val itemPrices: Map[ItemId, BigDecimal] = Map(
    "Soup"    -> BigDecimal("0.65"),
    "Bread"   -> BigDecimal("0.80"),
    "Milk"    -> BigDecimal("1.30"),
    "Apples"  -> BigDecimal("1.00")
  )

  override def itemExists(name: String): Boolean = itemPrices.keys.exists(_ == name)

  override def itemPrice(name: String): Option[BigDecimal] = itemPrices.get(name)


}
