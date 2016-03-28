package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Loads Products from Data Grid as DataFrame and run filtering.
  */
object LoadDataFrame {

  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: LoadDataFrame <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = args
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-dataframe").setMaster(master).setGigaSpaceConfig(gsConfig))

    val df = sc.gridDataFrame[Product]()
    df.printSchema()
    val count = df.filter(df("quantity") < 5).count()
    println(s"Number of products with quantity < 5: $count")
    sc.stopGigaSpacesContext()
  }

}
