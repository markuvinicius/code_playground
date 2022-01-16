package com.markuvinicius.kafka.streams.processors;


import com.markuvinicius.kafka.streams.dto.StatusDTO;
import com.markuvinicius.kafka.streams.serializer.JsonDeserializer;
import com.markuvinicius.kafka.streams.serializer.JsonSerializer;
import com.markuvinicius.kafka.streams.transformers.JsonToTwitterStatusTransformer;
import com.markuvinicius.playground.avro.TwitterStatus;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class TwitterMessageNormalizeProcessor extends AbstractProcessor {

    private Serde<StatusDTO> jsonTweetMessageSerde;
    private JsonToTwitterStatusTransformer transformer;

    private final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
    private final Serde<TwitterStatus> valueSpecificAvroSerde = new SpecificAvroSerde<>();

    private final Logger logger = LoggerFactory.getLogger(TwitterMessageNormalizeProcessor.class);


    public TwitterMessageNormalizeProcessor(StreamsBuilder builder) {
        logger.info("Initializing Twitter Processor class");
        this.builder = builder;

        JsonSerializer<StatusDTO> jsonSerializer = new JsonSerializer();
        JsonDeserializer<StatusDTO> jsonDeserializer = new JsonDeserializer(StatusDTO.class);

        this.jsonTweetMessageSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
        this.transformer = new JsonToTwitterStatusTransformer();

        this.valueSpecificAvroSerde.configure(serdeConfig, false); // `false` for record values
    }

    @Override
    public void execute() {
        logger.info("Configuring executor");

        KStream<String, StatusDTO> source = builder
                .stream("twitter-stage-messages",
                        Consumed.with(Serdes.String(), jsonTweetMessageSerde));

        KStream<String, TwitterStatus> transformedMessages = source.map((key, value) -> new KeyValue(value.getLang(), transformer.transform(value)));

        transformedMessages.to("twitter-normalized-messages", Produced.with(Serdes.String(), valueSpecificAvroSerde));
    }
}

