#!/bin/bash

# usage: sh makesbt.sh PROJECT_NAME

projectRoot=$1
ScalaVer=$(scala -version 2>&1 /dev/null |cut -d " " -f5)

mkdir -p ${projectRoot}/src/{main,test}/{scala,java,resources}
mkdir -p ${projectRoot}/{project,target}

touch ${projectRoot}/README.md

echo "name := \"$projectRoot\"

version := \"1.0\"

scalaVersion := \"$ScalaVer\""> ${projectRoot}/build.sbt

echo "import sbt._
import Keys._

object $projectRoot extends Build {

  lazy val root = Project(
      \"${projectRoot}\",          // Project name
      file(\".\")         // Project root directory
    ).settings(         // Project settings
      Seq(
        version := \"1.0\",
        scalaVersion := \"$ScalaVer\"
      ): _*
    )
}"> ${projectRoot}/project/Build.scala
