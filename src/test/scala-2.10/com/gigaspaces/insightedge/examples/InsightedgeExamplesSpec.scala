package com.gigaspaces.insightedge.examples

import com.gigaspaces.insightedge.examples.basic.{LoadDataFrame, LoadRdd, LoadRddWithSql, SaveRdd}
import com.gigaspaces.insightedge.examples.mllib.SaveAndLoadMLModel
import com.gigaspaces.insightedge.examples.offheap.OffHeapPersistence
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

  it("should successfully save and load MLModel to/from from Data Grid") {
    SaveAndLoadMLModel.main(args)
  }

  it("should successfully persist to Data Grid") {
    OffHeapPersistence.main(args)
  }

  override protected def beforeAll() = {
    datagrid = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer("insightedge-examples-space").lookupGroups("insightedge")).create()
  }

}
