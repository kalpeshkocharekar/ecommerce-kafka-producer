package com.ecommerce.producer

import com.ecommerce.producer.sparkrecevier.{inputtopic, kafkabroker}
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{StringType, StructType}
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


    println("starting spark streaming job")
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkabroker)
      .option("subscribe", inputtopic)
      .load()

    val data_Schema: StructType = new StructType()
      .add("id", StringType)
      .add("name", StringType)

    val jsondf =  df.select(col("key").cast("String"),col("value").cast("String"))
      .withColumn("value",from_json(col("value"),data_Schema))
    val finaldf  = jsondf.select(col("key"),col("value.*"))
    finaldf.writeStream
      .format("console")
      .start()
      .awaitTermination()

    }


}
