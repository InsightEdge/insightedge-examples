package com.gigaspaces.insightedge.examples.geospatial

import com.gigaspaces.scala.annotation._
import org.openspaces.spatial.shapes.Point

import scala.beans.BeanProperty

case class GasStation(

                       @BeanProperty
                       @SpaceId
                       var id: Long,

                       @BeanProperty
                       var name: String,

                       @BeanProperty
                       @SpaceSpatialIndex
                       var location: Point

                     ) {

  def this() = this(-1, null, null)

}