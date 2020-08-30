package com.ecommerce.producer

import java.util.Properties

import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

object recordproducer {
  private val logger: Logger = Logger(LoggerFactory.getLogger(sparkrecevier.getClass.getCanonicalName))


  logger.info("starting job")

  def main(args: Array[String]): Unit = {

    writeToKafka("producttopic")
  }


  def writeToKafka(topic: String): Unit = {
    try {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      val producer = new KafkaProducer[String, String](props)
      val jsonstring =
        s"""{
           | "id": "0001",
           | "name": "Peter"
           |}
         """.stripMargin

      val record = new ProducerRecord[String, String](topic, "key", jsonstring)
      producer.send(record)
      producer.close()
      println(producer)
      logger.info("stopping spark job")
    } catch {
      case exc: Exception => logger.error(exc.toString())

    }
  }

}
