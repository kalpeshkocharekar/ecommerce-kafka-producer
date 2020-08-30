package com.ecommerce.producer

import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

import scala.util.Properties

object sparkstreaming {

  private val logger: Logger = Logger(LoggerFactory.getLogger(sparkstreaming.getClass.getCanonicalName))
  private final val sparkLogLevel = Properties.envOrElse("SPARK_LOG_LEVEL", "ERROR")
  private final val identityMapCapacity = 128
  def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder()
        .appName("structure streaming")
        .master("local[*]")
        .config("spark.driver.extraJavaOptions", "-Dlog4j.configuration=file:conf/log4j.properties")
        .getOrCreate()

      spark.sparkContext.setLogLevel("ERROR")

      val a =spark.read.parquet("/home/kalpesh/Downloads/casadimension.parquet")
      a.printSchema()

    }


}
