name := "EmojiClusterAnalysis"

version := "0.1"

// Starting Spark version 2.0, Spark is built with Scala 2.11 by default.
scalaVersion := "2.11.11"

// Spark Information
val sparkVersion = "2.1.1"
val akkaV = "2.5.3"

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

// allows us to include Reactors
resolvers ++= Seq(
  "Sonatype OSS Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at
    "https://oss.sonatype.org/content/repositories/releases"
)
libraryDependencies ++= Seq(
  "io.reactors" %% "reactors" % "0.8"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"% akkaV
)

libraryDependencies ++= Seq(
  "org.jsoup"         % "jsoup"% "1.8+"
)

// main class for runtime
mainClass in Compile := Some("spideremoji.Spider")
