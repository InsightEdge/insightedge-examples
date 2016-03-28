package com.gigaspaces.insightedge.examples.streaming

import com.gigaspaces.scala.annotation._

import scala.beans.BeanProperty

/**
  * @author Oleksiy_Dyagilev
  */
case class TopTags(

                    @SpaceId(autoGenerate = true)
                    @BeanProperty
                    var id: String,

                    @BeanProperty
                    @SpaceProperty
                    var tagsCount: java.util.Map[Int, String]

                  ) {

  def this(tagsCount: java.util.Map[Int, String]) = this(null, tagsCount)

  def this() = this(null)

}
