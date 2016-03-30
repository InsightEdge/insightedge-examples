package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.scala.annotation._
import com.gigaspaces.spark.model.GridModel

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

                  ) extends GridModel {

  def this() = this(-1, null, -1, false)

}