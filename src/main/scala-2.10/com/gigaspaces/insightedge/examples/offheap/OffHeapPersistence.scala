package com.gigaspaces.insightedge.examples.offheap

import com.gigaspaces.insightedge.examples.basic.Product
import com.gigaspaces.spark.context.GigaSpacesConfig
import com.gigaspaces.spark.implicits._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * Persists RDD to OFF_HEAP storing it in Data Grid.
  */
object OffHeapPersistence {

  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: OffHeapPersistence <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = args
    val gsConfig = GigaSpacesConfig(space, Some(groups), Some(locators))
    val sparkConfig = new SparkConf()
      .setAppName("example-offheap")
      .setMaster(master)
      .setGigaSpaceConfig(gsConfig)
      .set("spark.externalBlockStore.blockManager", "org.apache.spark.storage.GigaSpacesBlockManager")
    val sc = new SparkContext(sparkConfig)

    val rdd = sc.parallelize((1 to 10).map { i =>
      Product(i, "Description of product " + i, Random.nextInt(10), Random.nextBoolean())
    })

    println(s"Counting products before persist: ${rdd.count()}")

    rdd.persist(StorageLevel.OFF_HEAP)

    println(s"Counting products after persist: ${rdd.count()}")

    sc.stopGigaSpacesContext()
  }

}
