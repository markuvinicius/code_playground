package com.markuvinicius.kafka.streams.processors;

import com.markuvinicius.kafka.streams.dto.JsonTweetMessage;
import com.markuvinicius.kafka.streams.serializer.JsonDeserializer;
import com.markuvinicius.kafka.streams.serializer.JsonSerializer;
import com.markuvinicius.kafka.streams.transformers.JsonToBasicMessageTransformer;
import com.markuvinicius.playground.avro.TweetMessage;
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

public class MessageNormalizeProcessor extends AbstractProcessor {

    private Serde<JsonTweetMessage> jsonTweetMessageSerde;
    private JsonToBasicMessageTransformer transformer;

    private final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
    private final Serde<TweetMessage> valueSpecificAvroSerde = new SpecificAvroSerde<>();

    private final Logger logger = LoggerFactory.getLogger(MessageNormalizeProcessor.class);


    public MessageNormalizeProcessor(StreamsBuilder builder) {
        logger.info("Initializing Twitter Processor class");
        this.builder = builder;

        JsonSerializer<JsonTweetMessage> jsonSerializer = new JsonSerializer();
        JsonDeserializer<JsonTweetMessage> jsonDeserializer = new JsonDeserializer(JsonTweetMessage.class);

        this.jsonTweetMessageSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
        this.transformer = new JsonToBasicMessageTransformer();

        this.valueSpecificAvroSerde.configure(serdeConfig, false); // `false` for record values
    }

    @Override
    public void execute() {
        logger.info("Configuring executor");

        KStream<String, JsonTweetMessage> source = builder
                .stream("transform-messages",
                        Consumed.with(Serdes.String(), jsonTweetMessageSerde));

        KStream<String, TweetMessage> transformedMessages = source
                .map((key, value) -> new KeyValue<>(value.getUserName(), (TweetMessage) transformer.transform(value)));

        transformedMessages.to("normalized-messages", Produced.with(Serdes.String(), valueSpecificAvroSerde ));
    }
}

