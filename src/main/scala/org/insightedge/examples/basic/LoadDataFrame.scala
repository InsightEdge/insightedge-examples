package org.insightedge.examples.basic

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

/**
  * Loads Products from Data Grid as DataFrame and runs filtering.
  */
object LoadDataFrame {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: LoadDataFrame <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-dataframe").setMaster(master).setInsightEdgeConfig(config))

    val sqlContext = new SQLContext(sc)
    val df = sqlContext.read.grid.loadClass[Product]
    df.printSchema()
    val count = df.filter(df("quantity") < 5).count()
    println(s"Number of products with quantity < 5: $count")
    sc.stopInsightEdgeContext()
  }

}
