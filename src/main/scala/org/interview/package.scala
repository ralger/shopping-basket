package org

import org.interview.model.{AppliedOffer, ItemId, ItemPrice, ItemQuantity}

package object interview {
  type BasketWithPrices = Map[ItemId, (ItemQuantity, ItemPrice)]
  type OfferCalculator = BasketWithPrices => List[AppliedOffer]
}
