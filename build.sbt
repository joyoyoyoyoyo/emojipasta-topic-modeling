name := "EmojiClusterAnalysis"

version := "0.1"

// Starting Spark version 2.0, Spark is built with Scala 2.11 by default.
scalaVersion := "2.11.11"

// Spark Information
val sparkVersion = "2.1.1"

// allows us to include spark packages
resolvers += "bintray-spark-packages" at
  "https://dl.bintray.com/spark-packages/maven/"

resolvers += "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers += "MavenRepository" at
  "https://mvnrepository.com/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion
)
mainClass in Compile := Some("EmojiClusterAnalysis")
