package com.ecommerce.producer

//import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import javax.print.attribute.standard.PrinterLocation
import org.apache.spark.sql.SparkSession
import scala.util.Properties
import org.apache.log4j.{Level, Logger}


object sparkrecevier {
  private final val SparkLogLevel = "ERROR"
  val kafkabroker = "localhost:9092"
  val inputtopic = "producttopic"
//  private val logger: Logger = Logger(LoggerFactory.getLogger(sparkrecevier.getClass.getCanonicalName))
//  private final val sparkLogLevel = Properties.envOrElse("SPARK_LOG_LEVEL", "ERROR")
//  logger.info("starting job")


  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .master("local[*]")
      .config("spark.driver.extraJavaOptions","-Dlog4j.configuration=file:/home/kalpesh/IdeaProjects/ecommerce-kafka-producer/conf/log4j.properties")
      .appName("test")
      .getOrCreate()
//    println("log level " + sparkLogLevel)
    sparkSession.sparkContext.setLogLevel(SparkLogLevel)
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

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
