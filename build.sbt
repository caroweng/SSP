name := "Software Engineering"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"


coverageExcludedPackages := ".*view.*"

lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
    "org.openjfx" % s"javafx-$m" % "11" classifier "linux"
)