import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "rpscala-play"
    val appVersion      = "1.0-SNAPSHOT"
    val dspScalaVersion = "2.10.3"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
