import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "HTML5ComicBookReader"
    val appVersion      = "0.0"

    val appDependencies = Seq(
        
      "mysql" % "mysql-connector-java" % "5.5.37"
      
    )



}