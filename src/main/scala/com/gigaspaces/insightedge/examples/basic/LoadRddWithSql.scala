package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Partially loads Product RDD from Data Grid using SQL query (filter on Data Grid side) and prints objects count.
  */
object LoadRddWithSql {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: LoadRddWithSql <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-rdd-sql").setMaster(master).setGigaSpaceConfig(gsConfig))

    val rdd = sc.gridSql[Product]("quantity < 5")
    println(s"Number of products with quantity < 5: ${rdd.count()}")
    sc.stopGigaSpacesContext()
  }

}
