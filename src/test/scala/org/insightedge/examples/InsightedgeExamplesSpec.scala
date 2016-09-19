package org.insightedge.examples

import org.insightedge.examples.basic._
import org.insightedge.examples.geospatial.{LoadDataFrameWithGeospatial, LoadRddWithGeospatial}
import org.insightedge.examples.mllib.SaveAndLoadMLModel
import org.openspaces.core.space.EmbeddedSpaceConfigurer
import org.openspaces.core.{GigaSpace, GigaSpaceConfigurer}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSpec}

class InsightedgeExamplesSpec extends FunSpec with BeforeAndAfterAll with BeforeAndAfterEach {
  val spaceName = "insightedge-examples-space"
  val lookupGroup = "insightedge"
  val lookupLocator = "localhost:4174"
  val args = Array("local[2]", spaceName, lookupGroup, lookupLocator)

  var datagrid: GigaSpace = _

  it("should successfully save RDD to Data Grid") {
    SaveRdd.main(args)
  }

  it("should successfully load RDD from Data Grid") {
    LoadRdd.main(args)
  }

  it("should successfully load RDD from Data Grid with SQL") {
    LoadRddWithSql.main(args)
  }

  it("should successfully load DataFrame from Data Grid") {
    LoadDataFrame.main(args)
  }

  it("should successfully persist DataFrame to Data Grid") {
    PersistDataFrame.main(args)
  }

  it("should successfully save and load MLModel to/from from Data Grid") {
    SaveAndLoadMLModel.main(args)
  }

  it("should successfully load rdd with geospatial SQL") {
    LoadRddWithGeospatial.main(args)
  }

  it("should successfully load dataframe with geospatial SQL") {
    LoadDataFrameWithGeospatial.main(args)
  }

  override protected def beforeAll() = {
    datagrid = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer("insightedge-examples-space").lookupGroups("insightedge").lookupLocators(lookupLocator)).create()
  }

}
