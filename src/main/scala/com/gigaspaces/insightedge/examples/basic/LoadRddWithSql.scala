package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Partially loads Product RDD from Data Grid using SQL query (filter on Data Grid side) and prints objects count.
  */
object LoadRddWithSql {

  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: LoadRddWithSql <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = args
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-rdd-sql").setMaster(master).setGigaSpaceConfig(gsConfig))

    val rdd = sc.gridSql[Product]("quantity < 5")
    println(s"Number of products with quantity < 5: ${rdd.count()}")
    sc.stopGigaSpacesContext()
  }

}
