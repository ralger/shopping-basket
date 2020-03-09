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

  it should "return an empty priced basket when passed no items to price" in {
    val basketItems = List()
    val result = widgetShop.findBasketItemsNotStocked(Basket(basketItems))
    result.size shouldBe 0
  }

  it should "price a basket of items" in {
    val basketItems = List(WIDGET_ITEM)
    val result = widgetShop.basketPrices(Basket(basketItems))
    result.size shouldBe 1
    result.head shouldBe (WIDGET_ITEM, (1, BigDecimal(10)))
  }

  it should "not price items that don't exist in the shop" in {
    val itemNotInShop = "SomeWidget"
    val basketItems = List(WIDGET_ITEM, itemNotInShop)
    val result = widgetShop.basketPrices(Basket(basketItems))
    result.get(itemNotInShop) shouldBe None
  }

}
