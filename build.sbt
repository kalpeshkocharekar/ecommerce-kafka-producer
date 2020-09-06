import sbt._
import Keys._


name := "ecommerce-kafka-producer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.1.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.25"
// https://mvnrepository.com/artifact/org.apache.kafka/connect-json
libraryDependencies += "org.apache.kafka" % "connect-json" % "2.6.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.5"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.5"

// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.30.1"


dependencyOverrides ++= {
  Seq(
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.1",
  )
}

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
//libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.5"
//dependencyOverrides ++= {
//  Seq(
//    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.7.1",
//    "com.fasterxml.jackson.core" %% "jackson-databind" % "2.6.7",
//    "com.fasterxml.jackson.core" %% "jackson-core" % "2.6.7"
//  )
//}

resolvers += Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/eed3si9n/sbt-plugins/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")

assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}


//mergeStrategy in assembly := {
//  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//  case x => MergeStrategy.first
//}

//
//assemblyMergeStrategy in assembly := {
//  case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
//  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
//  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
//  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
//  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
//  case PathList("com", "google", xs @ _*) => MergeStrategy.last
//  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
//  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
//  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
//  case "about.html" => MergeStrategy.rename
//  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
//  case "META-INF/mailcap" => MergeStrategy.last
//  case "META-INF/mimetypes.default" => MergeStrategy.last
//  case "plugin.properties" => MergeStrategy.last
//  case "log4j.properties" => MergeStrategy.last
//  case x =>
//    val oldStrategy = (assemblyMergeStrategy in assembly).value
//    oldStrategy(x)
//}