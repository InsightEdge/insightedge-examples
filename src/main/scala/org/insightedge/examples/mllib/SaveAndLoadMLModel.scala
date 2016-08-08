package org.insightedge.examples.mllib

import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

/**
  * Saves/reloads the ML model to/from Data Grid.
  */
object SaveAndLoadMLModel {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: SaveAndLoadMLModel <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-mllib").setMaster(master).setInsightEdgeConfig(config))

    val modelName = "decisionTreeModel"
    val model = createModel(sc)
    println(s"Saving ${model.getClass.getSimpleName} to the datagrid")
    model.saveToGrid(sc, modelName)
    println(s"Loading $modelName from the datagrid")
    val loadedModel = sc.loadMLInstance[KMeansModel](modelName).get
    println(s"Model ${loadedModel.getClass.getSimpleName} is loaded")
    sc.stopInsightEdgeContext()
  }

  private def createModel(sc: SparkContext) = {
    val vectors = List(Vectors.dense(1.0, 1.0, 3.0), Vectors.dense(2.0, 0.0, 1.0), Vectors.dense(2.0, 1.0, 0.0))
    val rdd = sc.parallelize(vectors)
    val k = 2
    val maxIterations = 100
    KMeans.train(rdd, k, maxIterations)
  }

}
