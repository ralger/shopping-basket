package org.interview

import org.interview.OfferCalculator
import org.interview.model._
import org.scalatest.FlatSpec
import org.scalatest.Matchers

import scala.collection.immutable.Range

class BasketPricerTest extends FlatSpec with Matchers {

  val noOffersBasketPricer: BasketPricer = new BasketPricer {
    override val offers: Map[DiscountName, OfferCalculator] = Map()
  }

  val fixedOfferDiscount = BigDecimal(0.1)
  val fixedOfferName = "Some Offer"
  val fixedOfferCalculator : OfferCalculator = _ => Some(fixedOfferDiscount)
  val fixedOfferBasketPricer: BasketPricer = new BasketPricer {
    override val offers: Map[DiscountName, OfferCalculator] = Map(fixedOfferName -> fixedOfferCalculator)
  }

  val emptyBasket: BasketWithPrices = Map()

  val aFewAppliedOffers = List(
    AppliedOffer("Offer Name", BigDecimal(0.5)),
    AppliedOffer("Another Name", BigDecimal(3)),
  )

  "BasketPricer" should "price an empty basket as zero subTotal irrespective of offers" in {
    val resultNoOffers = noOffersBasketPricer.priceBasketSubTotal(emptyBasket)
    val resultFixedOffer = fixedOfferBasketPricer.priceBasketSubTotal(emptyBasket)
    resultNoOffers shouldBe BigDecimal(0)
    resultFixedOffer shouldBe BigDecimal(0)
  }

  it should "price a basket of items with the correct sub total" in {
    val basket: BasketWithPrices = Map(
      "ItemA" -> (1, BigDecimal(3.50)),
      "ItemB" -> (2, BigDecimal(4.01)),
      "ItemC" -> (4, BigDecimal(0.05))
    )
    val result = noOffersBasketPricer.priceBasketSubTotal(basket)
    result shouldBe BigDecimal(11.72)
  }

  // We test the calculateOffers using a mocked OfferCalculator so that we are only testing the method
  // and not indirectly testing functionality in the OfferCalculators object (which have their own tests)
  it should "apply provided offers" in {
    val result = fixedOfferBasketPricer.calculateOffers(emptyBasket)
    result.size shouldBe 1
    result.head shouldBe AppliedOffer(fixedOfferName, fixedOfferDiscount)
  }

  it should "calculate the total price correctly given a set of applied offers" in {
    val result = fixedOfferBasketPricer.calculateTotalPrice(BigDecimal(10), aFewAppliedOffers)
    result shouldBe BigDecimal(6.5)
  }

  it should "not allow a negative total price" in {
    val result = fixedOfferBasketPricer.calculateTotalPrice(BigDecimal(1), aFewAppliedOffers)
    result should be >= BigDecimal(0)
  }





}
