package org.insightedge.examples.structured.streaming

/**
  *
  * Created by tamirs on 6/27/17.
  */

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._
/**
  * Counts words in UTF8 encoded, '\n' delimited text received from the network.
  *
  * Usage: StructuredNetworkWordCount <hostname> <port>
  * <hostname> and <port> describe the TCP server that Structured Streaming
  * would connect to receive data.
  *
  * To run this on your local machine, you need to first run a Netcat server
  *    `$ nc -lk 9999`
  * and then run the example
  *    `$ bin/run-example sql.streaming.StructuredNetworkWordCount
  *    localhost 9999`
  */

object StructuredNetworkWordCount {
  def main(args: Array[String]) {
//    val settings = if (args.length > 0) args else Array("local[*]", "localhost", "9999")

      val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174", "localhost", "9999")
    if (settings.length < 6) {
      System.err.println("Usage: LoadRdd <spark master url> <space name> <space groups> <space locator> <hostApp> <portApp>")
      System.exit(1)
    }

//    val Array(master, host, port) = settings
    val Array(master, space, groups, locators, host, port) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))

    val spark: SparkSession = SparkSession
     .builder
     .appName("example-structured-network-word-count")
     .master(master)
     .insightEdgeConfig(config)
     .getOrCreate()


    import spark.implicits._

    // Create DataFrame representing the stream of input lines from connection to host:port
    val lines = spark.readStream
      .format("socket")
      .option("host", host)
      .option("port", port.toInt)
      .load()

    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts: DataFrame = words.groupBy("value").count()

    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
//        .format("console")
      .format("org.apache.spark.sql.insightedge")
      .option("checkpointLocation", "/home/tamirs/myFiles/ie_build/manualBuild/streamBuild/logs/")
      .start()

    query.awaitTermination()
  }
}
