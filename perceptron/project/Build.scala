import sbt._
import Keys._

object perceptron extends Build {

  lazy val root = Project(
      "perceptron",          // Project name
      file(".")         // Project root directory
    ).settings(         // Project settings
      Seq(
        version := "1.0",
        scalaVersion := "2.10.1"
      ): _*
    )
}
