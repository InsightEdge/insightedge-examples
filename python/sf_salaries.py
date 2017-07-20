import sys
import os
from pyspark.sql import SparkSession

# InsightEdge config
if len(sys.argv) < 4:
    spaceName = "insightedge-space"
    lookupGroup = "insightedge"
    lookupLocator = "127.0.0.1:4174"
else:
    spaceName = sys.argv[1]
    lookupGroup = sys.argv[2]
    lookupLocator = sys.argv[3]

print("InsightEdge config: %s %s %s" % (spaceName, lookupGroup, lookupLocator))

spark = SparkSession \
    .builder \
    .appName("SF Salaries Example") \
    .config("spark.insightedge.space.name", spaceName) \
    .config("spark.insightedge.space.lookup.group", lookupGroup) \
    .config("spark.insightedge.space.lookup.locator", lookupLocator) \
    .getOrCreate()


# load SF salaries dataset from file
jsonFilePath = os.path.join(os.environ["I9E_HOME"], "/insightedge/data/sf_salaries_sample.json")
jsonDf = spark.read.json(jsonFilePath)

# save DataFrame to the grid
jsonDf.write.format("org.apache.spark.sql.insightedge").mode("overwrite").save("salaries")

# load DataFrame from the grid
gridDf = spark.read.format("org.apache.spark.sql.insightedge").option("collection", "salaries").load()
gridDf.printSchema()

# register this DataFrame as a table
gridDf.registerTempTable("salaries")

# run SQL query
averagePay = spark.sql(
    """SELECT JobTitle, AVG(TotalPay) as AveragePay
       FROM salaries
       WHERE Year = 2012
       GROUP BY JobTitle
       ORDER BY AVG(TotalPay) DESC
       LIMIT 15""")

for each in averagePay.collect():
    print("%s: %s" % (each[0], each[1]))

spark.stop()