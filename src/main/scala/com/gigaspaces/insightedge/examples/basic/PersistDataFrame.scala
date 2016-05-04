package com.gigaspaces.insightedge.examples.basic

import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.sql.insightedge._
import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Persists a selection of Products to Data Grid, then loads it as new DataFrame.
  */
object PersistDataFrame {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: PersistDataFrame <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-persist-dataframe").setMaster(master).setGigaSpaceConfig(gsConfig))
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.grid.loadClass[Product]
    println("Product schema:")
    df.printSchema()

    df.select("id", "quantity").filter(df("quantity") < 5).write.grid.mode(SaveMode.Overwrite).save("smallStock")
    val persistedDf = sqlContext.read.grid.load("smallStock")

    val count = persistedDf.count()

    println(s"Number of products with quantity < 5: $count")
    sc.stopGigaSpacesContext()
  }

}