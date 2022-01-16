package com.markuvinicius.kafka.streams.producer;

import com.markuvinicius.kafka.streams.dto.StatusDTO;
import com.markuvinicius.kafka.streams.producer.listener.Twitter4JStreamListenner;
import com.markuvinicius.kafka.streams.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Properties;

public class Twitter4JProducer {

    public static KafkaProducer<String, StatusDTO> getProducer() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG,
                "kafka json producer");
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        JsonSerializer<StatusDTO> serializer = new JsonSerializer<>();
        KafkaProducer<String, StatusDTO> producer = new KafkaProducer(configProperties, Serdes.String().serializer(),serializer);
        return producer;
    }

    public static void streamFeed(){
        KafkaProducer<String, StatusDTO> producer = getProducer();
        Twitter4JStreamListenner listener = new Twitter4JStreamListenner(producer,"twitter-input-messages");
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        twitterStream.addListener(listener);
        String[] keywordsArray = { "palmeiras","libertadores","champion","soccer" };
        FilterQuery query = new FilterQuery().track(keywordsArray);
        twitterStream.filter(query);
    }


    public static void main(String[] args) {
        streamFeed();
    }
}
