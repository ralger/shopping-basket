package org.interview.simpleshop

import org.interview.{BasketPricer, BasketWithPrices, DiscountName, OfferCalculator}
import org.interview.OfferCalculators.{conditionalOnQuantityOffer, productPercentageDiscountOffer}
import org.interview.model.AppliedOffer

/*
  Some example offers (as per the technical assignment)

  Apples have a 10% discount off their normal price this week
  Buy 2 tins of soup and get a loaf of bread for half price

 */
object ExampleOfferBasketPricer extends BasketPricer {

  val offers: Map[DiscountName, OfferCalculator] = Map(
    "Apples 10% off" ->
        productPercentageDiscountOffer("Apples", 0.1),

    // Note: I have interpreted this discount to indicate that at most a single load of bread will be discounted half price
    // i.e. NOT "For every 2 tins of soup get a load of bread half price"
    // discountMaxQuantity parameter was introduced to enforce this
    "Buy 2 tins of soup and get a loaf of bread for half price" ->
        conditionalOnQuantityOffer(2, "Soup", productPercentageDiscountOffer("Bread", 0.5, 1)),
  )

}
