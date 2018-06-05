organization := "org.es.pr"

name := "scala-design-patters"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

javaOptions ++= Seq("-target", "1.8", "-source")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
