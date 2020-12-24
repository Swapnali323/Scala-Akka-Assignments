name := "assignment"

version := "0.1"

scalaVersion := "2.13.4"


val akkaVersion = "2.6.10"

val akkaHttpVersion = "10.2.2"

libraryDependencies ++=Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.2.2",
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  "org.slf4j" % "slf4j-api" % "1.7.9", "org.slf4j" % "slf4j-simple" % "1.7.8",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime
)

