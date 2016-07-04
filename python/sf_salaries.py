import sys
import os
from pyspark import SparkConf, SparkContext
from pyspark.sql import SQLContext

conf = SparkConf()
conf.setAppName("SF Salaries Example")

# InsightEdge config
if len(sys.argv) < 3:
    spaceName = "insightedge-space"
    lookupGroup = "insightedge"
    lookupLocator = "127.0.0.1:4174"
else:
    spaceName = sys.argv[1]
    lookupGroup = sys.argv[2]
    lookupLocator = sys.argv[3]

conf.set("spark.gigaspaces.space.name", spaceName)
conf.set("spark.gigaspaces.space.lookup.group", lookupGroup)
conf.set("spark.gigaspaces.space.lookup.locator", lookupLocator)

sc = SparkContext(conf=conf)
sqlContext = SQLContext(sc)

# load SF salaries dataset from file
jsonFilePath = os.path.join(os.environ["INSIGHTEDGE_HOME"], "data/sf_salaries_sample.json")
jsonDf = sqlContext.read.json(jsonFilePath)

# save DataFrame to the grid
jsonDf.write.format("org.apache.spark.sql.insightedge").mode("overwrite").save("salaries")

# load DataFrame from the grid
gridDf = sqlContext.read.format("org.apache.spark.sql.insightedge").option("collection", "salaries").load()
gridDf.printSchema()

# register this DataFrame as a table
gridDf.registerTempTable("salaries")

# run SQL query
averagePay = sqlContext.sql(
    """SELECT JobTitle, AVG(TotalPay) as AveragePay
       FROM salaries
       WHERE Year = 2012
       GROUP BY JobTitle
       ORDER BY AVG(TotalPay) DESC
       LIMIT 15""")

for each in averagePay.collect():
    print("%s: %s" % (each[0], each[1]))

sc.stop()
