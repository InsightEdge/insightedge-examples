name := "insightedge-examples"
version := "0.3.0"
scalaVersion := "2.10.6"

resolvers += Resolver.mavenLocal
libraryDependencies += "com.gigaspaces.insightedge" % "insightedge-core" % "0.3.0" % "provided"
libraryDependencies += "com.gigaspaces.insightedge" % "gigaspaces-scala" % "0.3.0" % "provided"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.0"
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

test in assembly := {}
assemblyOutputPath in assembly := new File("target/insightedge-examples.jar")
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)