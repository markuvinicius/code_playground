package com.markuvinicius.kafka.streams.producer.listener;

import com.markuvinicius.kafka.streams.dto.JsonTweetMessage;
import com.markuvinicius.kafka.streams.dto.StatusDTO;
import com.markuvinicius.kafka.streams.dto.UserDTO;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

public class Twitter4JStreamListenner implements twitter4j.StatusListener {
    private final Logger logger = LoggerFactory.getLogger(Twitter4JStreamListenner.class);

    private KafkaProducer<String,StatusDTO> producer;
    private String topicName;

    public Twitter4JStreamListenner(KafkaProducer<String, StatusDTO> producer, String topicName) {
        this.producer = producer;
        this.topicName = topicName;
    }

    @Override
    public void onStatus(Status status) {
        UserDTO userDTO = UserDTO.Builder.anUserDTO()
                .withId(status.getUser().getId())
                .withCreatedAt(status.getUser().getCreatedAt())
                .withName(status.getUser().getName())
                .withLang(status.getUser().getLang())
                .withEmail(status.getUser().getEmail())
                .withFollowersCount(status.getUser().getFollowersCount())
                .withScreenName(status.getUser().getScreenName())
                .build();

        StatusDTO statusDTO = StatusDTO.Builder.aStatusDTO()
                .withId(status.getId())
                .withCreatedAt(status.getCreatedAt())
                .withLang(status.getLang())
                .withFavoriteCount(status.getFavoriteCount())
                .withRetweetCount(status.getRetweetCount())
                .withText(status.getText())
                .withUser(userDTO)
                .withLocation(status.getPlace().getCountry())
                .build();

        logger.info(status.toString());

        ProducerRecord<String,StatusDTO> producerRecord = new ProducerRecord(topicName,statusDTO);
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null){
                    logger.error("Error sending message: " + e.getMessage());
                    e.printStackTrace();
                }else{
                    logger.info("Message Sent: " + statusDTO.toString());
                }
            }
        });
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {
        logger.info("Message limitation: " + i);

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }
}
