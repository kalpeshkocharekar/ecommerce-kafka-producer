package com.ecommerce.producer

import java.util.Properties
import java.util.UUID.randomUUID
import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

object recordproducer {
  private val logger: Logger = Logger(LoggerFactory.getLogger(sparkrecevier.getClass.getCanonicalName))


  logger.info("starting job")

  def main(args: Array[String]): Unit = {
    while (true) {
      writeToKafka("producttopic")
      Thread.sleep(5000)
    }
  }


  def writeToKafka(topic: String): Unit = {
    try {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      val producer = new KafkaProducer[String, String](props)
      val start = 1
      val end = 20
      val rnd = new scala.util.Random
      val random_customer_id = start + rnd.nextInt((end - start) + 1)
      val random_product_id = start + rnd.nextInt((end - start) + 1)
      val start_quantity = 1
      val end_quantity = 5
      val random_quantity = start + rnd.nextInt((end_quantity - start_quantity) + 1)

            val jsonstring =
              s"""{
                 | "order_id": "${randomUUID().toString()}",
                 | "product_id": "${random_product_id.toString()}",
                 |  "customer_id": "${random_customer_id.toString() }",
                 |   "quantity": "${random_quantity.toString()}"
                 |}
               """.stripMargin


      print(jsonstring)
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
