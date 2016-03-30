package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Loads Product RDD from Data Grid and prints objects count.
  */
object LoadRdd {

  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: LoadRdd <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = args
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-load-rdd").setMaster(master).setGigaSpaceConfig(gsConfig))

    val rdd = sc.gridRdd[Product]()
    println(s"Products RDD count: ${rdd.count()}")
    sc.stopGigaSpacesContext()
  }

}
