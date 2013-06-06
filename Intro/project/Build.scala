import sbt._
import Keys._

object Intro extends Build {

  lazy val root = Project(
      "Intro",          // Project name
      file(".")         // Project root directory
    ).settings(         // Project settings
      Seq(
        version := "1.0",
        scalaVersion := "2.10.1"
      ): _*
    )
}
