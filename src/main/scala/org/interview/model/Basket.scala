package org.interview.model

case class Basket(items: Map[ItemId, ItemQuantity])

object Basket {
  def apply(items: List[ItemId]): Basket = {
    Basket(items.groupMapReduce(identity)(_ => 1: ItemQuantity)(_ + _))
  }
}