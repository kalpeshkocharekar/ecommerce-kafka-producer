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
           | "order_id": "0001",
           | "product_id": "Peter"
           |  "customer_id": "Peter"
           |   "quantity": "Peter"
           |}
         """.stripMargin

      val record = new ProducerRecord[String, String](topic, "testtable", jsonstring)
      producer.send(record)
      producer.close()
      println(producer)
      logger.info("stopping spark job")
    } catch {
      case exc: Exception => logger.error(exc.toString())

    }
  }

}
