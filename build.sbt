name := "assignment"

version := "0.1"

scalaVersion := "2.13.4"


val akkaVersion = "2.6.10"

val akkaHttpVersion = "10.2.2"

libraryDependencies ++=Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)

