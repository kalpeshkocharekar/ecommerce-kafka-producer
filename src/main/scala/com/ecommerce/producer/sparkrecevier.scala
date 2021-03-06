package com.ecommerce.producer


import org.apache.spark.sql.SparkSession


object sparkrecevier {
  private final val SparkLogLevel = "ERROR"
  val kafkabroker = "localhost:9092"
  val inputtopic = "producttopic"

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("test")
      .getOrCreate()
    println("starting job")

    sparkSession.sparkContext.setLogLevel(SparkLogLevel)

    val df = sparkSession
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkabroker)
      .option("subscribe", inputtopic)
      .load()

    df.writeStream
      .format("console")
      .start()
      .awaitTermination()
  }
}
