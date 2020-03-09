package org.interview

import org.interview.model._
import org.interview.simpleshop.{ExampleShop, ExampleOfferBasketPricer}

object PriceBasketApp extends App {

  val basket = Basket(args.toList)
  val basketItemsWithPrices = ExampleShop.basketPrices(basket)
  val basketSubTotal = ExampleOfferBasketPricer.priceBasketSubTotal(basketItemsWithPrices)
  val offersApplied = ExampleOfferBasketPricer.calculateOffers(basketItemsWithPrices)
  val totalPrice = ExampleOfferBasketPricer.calculateTotalPrice(basketSubTotal, offersApplied)


  println(f"Subtotal: £$basketSubTotal%1.2f")
  if (offersApplied.isEmpty) {
    println(s"(No offers available)")
  } else {
    offersApplied.foreach { offerApplied =>
      println(f"${offerApplied.discountName}: £${offerApplied.discountApplied}%1.2f")
    }
  }
  println(f"Total price: £$totalPrice%1.2f")


}
