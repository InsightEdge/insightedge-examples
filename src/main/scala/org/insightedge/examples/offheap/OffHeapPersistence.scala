package org.insightedge.examples.offheap

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.examples.basic.Product
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._

import scala.util.Random

/**
  * Persists RDD to OFF_HEAP storing it in Data Grid.
  */
object OffHeapPersistence {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: OffHeapPersistence <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val sparkConfig = new SparkConf()
      .setAppName("example-offheap")
      .setMaster(master)
      .setInsightEdgeConfig(config)
      .set("spark.externalBlockStore.blockManager", "org.apache.spark.storage.InsightEdgeBlockManager")
    val sc = new SparkContext(sparkConfig)

    val rdd = sc.parallelize((1 to 10).map { i =>
      Product(i, "Description of product " + i, Random.nextInt(10), Random.nextBoolean())
    })

    println(s"Counting products before persist: ${rdd.count()}")

    rdd.persist(StorageLevel.OFF_HEAP)

    println(s"Counting products after persist: ${rdd.count()}")

    sc.stopInsightEdgeContext()
  }

}
