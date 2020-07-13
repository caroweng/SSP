import sbt.Keys.libraryDependencies

name := "Software Engineering"

version := "0.1"


scalaVersion := "2.12.7"
lazy val mainDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3",
    "com.google.inject" % "guice" % "4.1.0",
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
    "com.typesafe.play" %% "play-json" % "2.6.6",
    "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3",
    "org.scalafx" %% "scalafx" % "11-R16",

)
coverageExcludedPackages := ".*view.*"

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
//libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"
//
//
//libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
//libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
//
//libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
//
//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
//
//libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"
//
//
//// Add dependency on ScalaFX library
//libraryDependencies += "org.scalafx" %% "scalafx" % "11-R16"
//
//coverageExcludedPackages := ".*view.*"

lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
    "org.openjfx" % s"javafx-$m" % "11" classifier "linux"
)


// Own Modules
lazy val root = project
    .in(file("."))
    .aggregate(gameModule, playerModule)
    .settings(
        aggregate in update := false
    )

lazy val gameModule = project
    .in(file("gameModule"))
    .settings(libraryDependencies ++= mainDependencies)
    .dependsOn(playerModule)
lazy val playerModule = project
    .in(file("playerModule"))
    .settings(libraryDependencies ++= mainDependencies)
