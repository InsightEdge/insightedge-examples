name := "insightedge-examples"
version := "0.3.0"
scalaVersion := "2.10.6"

resolvers += Resolver.mavenLocal
libraryDependencies += "com.gigaspaces.insightedge" % "insightedge-core" % "0.3.0"
libraryDependencies += "com.gigaspaces.insightedge" % "gigaspaces-scala" % "0.3.0"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.0"
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"