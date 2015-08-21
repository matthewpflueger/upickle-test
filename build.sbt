name := "upickle-test"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.lihaoyi" %% "autowire" % "0.2.5"
libraryDependencies += "com.lihaoyi" %% "upickle" % "SNAPSHOT" //"0.3.5"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.3.1"

testFrameworks += new TestFramework("utest.runner.Framework")
