import sbt._
import Keys._

object bigramlm extends Build {

  lazy val root = Project(
      "bigramlm",          // Project name
      file(".")         // Project root directory
    ).settings(         // Project settings
      Seq(
        version := "1.0",
        scalaVersion := "2.10.1"
      ): _*
    )
}
