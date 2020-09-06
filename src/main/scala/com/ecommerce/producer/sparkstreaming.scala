package com.ecommerce.producer

import com.ecommerce.producer.sparkrecevier.{inputtopic, kafkabroker}
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
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
      .add("order_id", StringType)
      .add("product_id", StringType)
      .add("customer_id", StringType)
      .add("quantity", StringType)
    //      val data_Schema: StructType = new StructType()
    //        .add("order_id", StringType)
    //        .add("product_id", StringType)
    //        .add("customer_id",StringType)
    //        .add("quantity",StringType)


    val jsondf = df.select(col("key").cast("String"), col("value"))
      .withColumn("value", from_json(col("value").cast("String"), data_Schema))

    val finaldf = jsondf.select(col("key"), col("value.*"))

    val typecastdf = finaldf
      .withColumn("product_id", col("product_id").cast("Integer"))
      .withColumn("customer_id", col("customer_id").cast("Integer"))
      .withColumn("quantity", col("quantity").cast("Integer"))

    typecastdf.printSchema()
//
//    val customertabledf = spark.read
//      .format("jdbc")
//      .option("driver","org.sqlite.JDBC")
//      .option("dbtable","testtable")
//      .option("url","/home/kalpesh/IdeaProjects/ecommerce-kafka-producer/devdb.sqlite")
//      .load()
//
//    val joineddf = customertabledf.join(typecastdf,col("id"))
//
    typecastdf.writeStream
      .format("console")
      .start()
      .awaitTermination()

  }


}
