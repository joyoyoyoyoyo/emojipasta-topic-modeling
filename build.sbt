name := "EmojiClusterAnalysis"

version := "0.1"

// Starting Spark version 2.0, Spark is built with Scala 2.11 by default.
scalaVersion := "2.11.11"


// Resolvers
resolvers += "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers += "MavenRepository" at
  "https://mvnrepository.com/"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at
    "https://oss.sonatype.org/content/repositories/releases"
)

// main class for runtime
mainClass in Compile := Some("smilefrontend.Temp")
