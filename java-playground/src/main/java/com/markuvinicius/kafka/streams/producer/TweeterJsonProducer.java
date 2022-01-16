package com.markuvinicius.kafka.streams.producer;

import com.markuvinicius.kafka.streams.dto.JsonTweetMessage;
import com.markuvinicius.kafka.streams.serializer.JsonSerializer;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TweeterJsonProducer {

    public static Producer<String, JsonTweetMessage> getProducer() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG,
                "kafka json producer");
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        //KafkaJsonSerializer<JsonTweetMessage> serializer = new KafkaJsonSerializer();
        JsonSerializer<JsonTweetMessage> serializer = new JsonSerializer<>();
        Producer<String, JsonTweetMessage> producer = new KafkaProducer<>(configProperties,Serdes.String().serializer(),serializer);
        return producer;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Producer<String, JsonTweetMessage> producer = getProducer();

        while (true) {


            JsonTweetMessage message = new JsonTweetMessage("marku",
                    generateRandomTimestamp(),
                    "this is a message");

            ProducerRecord<String, JsonTweetMessage> record = new ProducerRecord<>("input-messages", message);
            System.out.println("mensagem enviada: " + record.value().toString());

            producer.send(record).get();
            Thread.sleep(150);
        }
    }

    public static String generateRandomTimestamp(){
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2021-11-27 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand.toString();
    }
}
