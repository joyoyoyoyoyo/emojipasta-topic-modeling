val dependencies = "net.ruippeixotog" %% "scala-scraper" % "2.0.0"

val typeSafeResolver = "Typesafe Simple Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"


lazy val commonSettings = Seq(
  organization := "Angel Gabriel Ortega",
  name := "EmojiClusterAnalysis",
  version := "0.1.X",
  scalaVersion := "2.11.11",

  // main class for runtime
  mainClass in Compile := Some("Temp")
)

lazy val core = (project in file("."))
    .settings(
      commonSettings,
      libraryDependencies += dependencies,
      resolvers += typeSafeResolver
    )

