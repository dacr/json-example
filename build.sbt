import AssemblyKeys._

seq(assemblySettings: _*)

name := "json-example"

version := "0.0.1"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "ISO-8859-15")

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "dummy.jar"

libraryDependencies ++= Seq(
  "org.json4s"  %% "json4s-native"      % "3.2.4"
)

libraryDependencies ++= Seq(
   "org.scalatest" %% "scalatest" % "1.9.+" % "test",
   "junit" % "junit" % "4.+" % "test"
)

initialCommands in console := """import dummy._"""

