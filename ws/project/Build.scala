import sbt._
import Keys._

object ws extends Build {

  lazy val root = Project(
      "ws",          // Project name
      file(".")         // Project root directory
    ).settings(         // Project settings
      Seq(
        version := "1.0",
        scalaVersion := "2.10.2"
      ): _*
    )
}
