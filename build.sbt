name := "vesak-lantern"

version := "1.0-SNAPSHOT"

val Subcut = "com.escalatesoft.subcut" %% "subcut" % "2.0"

val TypesafeLogging = "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  Subcut,
  TypesafeLogging
)     

play.Project.playScalaSettings
