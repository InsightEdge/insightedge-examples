package com.gigaspaces.insightedge.examples.streaming

import com.gigaspaces.scala.annotation._
import com.gigaspaces.spark.model.GridModel

import scala.beans.BeanProperty

/**
  * @author Oleksiy_Dyagilev
  */
case class TopTags(

                    @SpaceId(autoGenerate = true)
                    @BeanProperty
                    var id: String,

                    @BeanProperty
                    var tagsCount: java.util.Map[Int, String],

                    @BeanProperty
                    var batchTime: Long

                  ) extends GridModel {

  def this(tagsCount: java.util.Map[Int, String]) = this(null, tagsCount, System.currentTimeMillis)

  def this() = this(null)

}
