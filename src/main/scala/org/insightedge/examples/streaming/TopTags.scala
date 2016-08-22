package org.insightedge.examples.streaming

import org.insightedge.scala.annotation._

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
                  ) {

  def this(tagsCount: java.util.Map[Int, String]) = this(null, tagsCount, System.currentTimeMillis)

  def this() = this(null)

}
