package org

import org.interview.model.{AppliedOffer, ItemId, ItemPrice, ItemQuantity}

package object interview {
  type BasketWithPrices = Map[ItemId, (ItemQuantity, ItemPrice)]
  type DiscountName = String
  type OfferCalculator = BasketWithPrices => Option[BigDecimal]
}
