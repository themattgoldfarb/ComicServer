name := "ComicServer"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  jdbc,
  anorm,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18"
)     

