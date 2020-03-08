package org.interview

import org.interview.BasketPricer.OfferCalculator
import org.interview.model._
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class BasketPricerTest extends FlatSpec with Matchers {

  private val emptyShop = new Shop {
    override def itemExists(name: String): Boolean = false
    override def itemPrice(name: String): Option[ItemPrice] = None
  }

  private val widgetShop = new Shop {
    override def itemExists(name: String): Boolean = name == "Widget"
    override def itemPrice(name: String): Option[ItemPrice] = if (name == "Widget") Some(BigDecimal(10)) else None
  }

  val noOffers: List[OfferCalculator] = List()


  val emptyBasket = Basket(List())

  "BasketPricer" should "find items that are missing from the shop" in {
    val basketItems = List("AmazingMachine", "SomeWidget")
    val result = BasketPricer.findMissingShopItems(Basket(basketItems), emptyShop)
    result.sorted shouldBe basketItems.sorted
  }

  it should "price an empty basket as zero subTotal" in {
    val result = BasketPricer.priceBasketSubTotal(BasketPricer.basketWithPrices(emptyBasket, widgetShop))
    result shouldBe BigDecimal(0)
  }

  it should "not price items that don't exist in the shop" in {
    val result = BasketPricer.priceBasketSubTotal(BasketPricer.basketWithPrices(Basket(List("AmazingMachine")), widgetShop))
    result shouldBe BigDecimal(0)
  }

  it should "correctly look up the price from the shop" in {
    val result = BasketPricer.basketWithPrices(Basket(List("Widget")), widgetShop)
    result.length shouldBe 1
    result.head._3 shouldBe BigDecimal(10)
  }

  it should "apply provided offer calculator" in {
    val fixedOffer = List(AppliedOffer("Fixed Amount offer", 0.1))
    val fixedOfferCalculator : OfferCalculator = _ => fixedOffer
    val result = BasketPricer.calculateOffers(BasketPricer.basketWithPrices(emptyBasket, emptyShop), List(fixedOfferCalculator))
    result shouldBe fixedOffer
  }



}
