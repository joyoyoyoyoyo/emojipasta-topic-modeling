name := "EmojiClusterAnalysis"

version := "0.1"

scalaVersion := "2.11.11"

resolvers += "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.0.0"

// main class for runtime
mainClass in Compile := Some("Temp")
