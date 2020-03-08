package org.interview.simpleshop

import org.interview.OfferCalculator
import org.interview.OfferCalculators.{conditionalOnQuantityOffer, productPercentageDiscountOffer}

/*
  Some example offers (as per the technical assignment)

  Apples have a 10% discount off their normal price this week
  Buy 2 tins of soup and get a loaf of bread for half price

 */
object ExampleShopOffers {
  val currentOffers: List[OfferCalculator] = List(
    productPercentageDiscountOffer("Apples", 0.1),
    conditionalOnQuantityOffer(2, "Soup", productPercentageDiscountOffer("Bread", 0.5, 1)),
  )
}
