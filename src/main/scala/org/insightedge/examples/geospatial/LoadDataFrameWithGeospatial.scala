/*
 * Copyright (c) 2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.insightedge.examples.geospatial

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._
import org.openspaces.spatial.ShapeFactory
import org.openspaces.spatial.shapes.Point

import scala.util.Random

/**
  * Saves Gas Stations with indexed location field to Data Grid, loads them with dataframes API. See all operations at http://insightedge.io/docs
  */
object LoadDataFrameWithGeospatial {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array( new SparkConf().get("spark.master", InsightEdgeConfig.SPARK_MASTER_LOCAL_URL_DEFAULT),
      sys.env.getOrElse(InsightEdgeConfig.INSIGHTEDGE_SPACE_NAME, InsightEdgeConfig.INSIGHTEDGE_SPACE_NAME_DEFAULT))

    if (settings.length != 2) {
      System.err.println("Usage: LoadDataFrameWithGeospatial <spark master url> <space name>")
      System.exit(1)
    }
    val Array(master, space) = settings
    val config = InsightEdgeConfig(space)
    val spark = SparkSession.builder
      .appName("example-load-dataframe-geospatial")
      .master(master)
      .insightEdgeConfig(config)
      .getOrCreate()
    val sc = spark.sparkContext

    val stations = (1 to 100000).map { i => GasStation(i, "Station" + i, randomPoint(-50, 50)) }
    println(s"Saving ${stations.size} gas stations RDD to the space")
    sc.parallelize(stations).saveToGrid()

    val userLocation = ShapeFactory.point(10, 10)
    val searchArea = ShapeFactory.circle(userLocation, 10)
    val df = spark.read.grid[GasStation]
    val countNearby = df.filter(df("location") geoWithin searchArea).count()
    println(s"Number of stations within 10 radius around user: $countNearby")

    spark.stopInsightEdgeContext()
  }

  def randomPoint(min: Double, max: Double): Point = {
    ShapeFactory.point(randomInRange(min, max), randomInRange(min, max))
  }

  def randomInRange(min: Double, max: Double): Double = {
    Random.nextDouble() * (max - min) + min
  }

}
