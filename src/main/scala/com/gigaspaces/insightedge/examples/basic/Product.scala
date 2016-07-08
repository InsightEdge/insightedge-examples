package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.scala.annotation._
import scala.beans.{BeanProperty, BooleanBeanProperty}

case class Product(
                    @BeanProperty
                    @SpaceId
                    var id: Long,

                    @BeanProperty
                    var description: String,

                    @BeanProperty
                    var quantity: Int,

                    @BooleanBeanProperty
                    var featuredProduct: Boolean
                  ) {

  def this() = this(-1, null, -1, false)

}