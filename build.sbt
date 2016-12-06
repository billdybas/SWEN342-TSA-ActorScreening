name := "TSA-Actor-Screening"

version := "1.0.0"

scalaVersion := "2.12.0"

scalacOptions := Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.14"
)
