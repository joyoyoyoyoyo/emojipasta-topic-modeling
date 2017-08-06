name := "EmojiClusterAnalysis"

version := "0.1"

// Starting Spark version 2.0, Spark is built with Scala 2.11 by default.
scalaVersion := "2.11.11"

resolvers += "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.0.0"

// main class for runtime
mainClass in Compile := Some("Temp")
