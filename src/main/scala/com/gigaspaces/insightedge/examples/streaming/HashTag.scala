package com.gigaspaces.insightedge.examples.streaming

import com.gigaspaces.scala.annotation._
import com.gigaspaces.spark.model.GridModel

import scala.beans.BeanProperty

/**
  * @author Oleksiy_Dyagilev
  */
case class HashTag(

                    @SpaceId(autoGenerate = true)
                    @BeanProperty
                    var id: String,

                    @BeanProperty
                    var tag: String

                  ) extends GridModel {

  def this(tag: String) = this(null, tag)

  def this() = this(null, null)

}
