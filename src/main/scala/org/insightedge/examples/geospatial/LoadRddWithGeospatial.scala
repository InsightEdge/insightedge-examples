package org.insightedge.examples.geospatial

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._
import org.openspaces.spatial.ShapeFactory
import org.openspaces.spatial.shapes.Point

import scala.util.Random

/**
  * Saves Gas Stations with indexed location field to Data Grid, loads them with SQL query. See all operations at http://insightedge.io/docs
  */
object LoadRddWithGeospatial {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: LoadRddWithGeospatial <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val spark = SparkSession.builder
      .appName("example-load-rdd-geospatial")
      .master(master)
      .insightEdgeConfig(config)
      .getOrCreate()
    val sc = spark.sparkContext

    val stations = (1 to 100000).map { i => GasStation(i, "Station" + i, randomPoint(-50, 50)) }
    println(s"Saving ${stations.size} gas stations RDD to the space")
    sc.parallelize(stations).saveToGrid()

    val userLocation = ShapeFactory.point(10, 10)
    val searchArea = ShapeFactory.circle(userLocation, 10)
    val stationsNearby = sc.gridSql[GasStation]("location spatial:within ?", Seq(searchArea))
    println(s"Number of stations within 10 radius around user: ${stationsNearby.count()}")

    sc.stopInsightEdgeContext()
  }

  def randomPoint(min: Double, max: Double): Point = {
    ShapeFactory.point(randomInRange(min, max), randomInRange(min, max))
  }

  def randomInRange(min: Double, max: Double): Double = {
    Random.nextDouble() * (max - min) + min
  }

}
