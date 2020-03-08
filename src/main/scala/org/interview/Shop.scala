package org.interview

import org.interview.model._

trait Shop {
  def itemExists(name: String): Boolean
  def itemPrice(name: String): Option[ItemPrice]
}
