package org.insightedge.examples.basic

import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._

/**
  * Loads Product RDD from Data Grid and prints objects count.
  */
object LoadRdd {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: LoadRdd <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-rdd").setMaster(master).setInsightEdgeConfig(config))

    val rdd = sc.gridRdd[Product]()
    println(s"Products RDD count: ${rdd.count()}")
    sc.stopInsightEdgeContext()
  }

}
