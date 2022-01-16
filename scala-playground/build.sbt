name := "scala-playground"

version := "0.1"

scalaVersion := "2.13.7"

idePackagePrefix := Some("com.markuvinicius.scala.playground")

// https://mvnrepository.com/artifact/org.apache.kafka/kafka
libraryDependencies += "org.apache.kafka" %% "kafka" % "3.0.0"
// https://mvnrepository.com/artifact/io.github.embeddedkafka/embedded-kafka
libraryDependencies += "io.github.embeddedkafka" %% "embedded-kafka" % "3.0.0" % Test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"


