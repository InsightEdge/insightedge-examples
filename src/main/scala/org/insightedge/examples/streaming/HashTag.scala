package org.insightedge.examples.streaming

import org.insightedge.scala.annotation._

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

                  ) {

  def this(tag: String) = this(null, tag)

  def this() = this(null, null)

}
