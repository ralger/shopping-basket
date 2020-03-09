package org.interview

import org.interview.model.{Basket, ItemPrice}
import org.scalatest.{FlatSpec, Matchers}

class ShopTest extends FlatSpec with Matchers  {

  private val WIDGET_ITEM = "Widget"

  private val emptyShop = new Shop {
    override def itemExists(name: String): Boolean = false
    override def itemPrice(name: String): Option[ItemPrice] = None
  }

  private val widgetShop = new Shop {
    override def itemExists(name: String): Boolean = name == WIDGET_ITEM
    override def itemPrice(name: String): Option[ItemPrice] = if (name == WIDGET_ITEM) Some(BigDecimal(10)) else None
  }

  "Shop" should "find basket items that are missing from the shop" in {
    val basketItems = List("AmazingMachine", "SomeWidget")
    val result = emptyShop.findBasketItemsNotStocked(Basket(basketItems))
    result.sorted shouldBe basketItems.sorted
  }

  it should "price a basket of items" in {
    val basketItems = List(WIDGET_ITEM)
    val result = widgetShop.basketPrices(Basket(basketItems))
    result.keys.toList shouldBe basketItems
  }

  it should "not price items that don't exist in the shop" in {
    val basketItems = List(WIDGET_ITEM, "SomeWidget")
    val result = widgetShop.basketPrices(Basket(basketItems))
    result.size shouldBe 1

  }


}
