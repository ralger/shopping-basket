package org

import org.interview.model.{AppliedOffer, ItemId, ItemPrice, ItemQuantity}

package object interview {
  type OfferCalculator = List[(ItemId, ItemQuantity, ItemPrice)] => List[AppliedOffer]
}
