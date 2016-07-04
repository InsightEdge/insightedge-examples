package com.gigaspaces.insightedge.examples.geospatial

import com.gigaspaces.scala.annotation._
import com.gigaspaces.spark.model.GridModel
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

                     ) extends GridModel {

  def this() = this(-1, null, null)

}