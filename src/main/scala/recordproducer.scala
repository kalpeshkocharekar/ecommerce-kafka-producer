import java.util.Properties

import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.typesafe.scalalogging
//import com.typesafe.scalalogging.Logger
import ch.qos.logback.classic.{Level,Logger}
import org.apache.kafka.clients.producer._
import org.slf4j.LoggerFactory

object recordproducer {

  val log = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).asInstanceOf[Logger]
  log.setLevel(Level.INFO)
  log.info("starting job")
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
        log.info("stopping spark job")
      }catch {
        case exc: Exception => log.error(exc.toString())

      }
    }

  }

