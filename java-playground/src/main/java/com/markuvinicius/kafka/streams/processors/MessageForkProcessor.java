package com.markuvinicius.kafka.streams.processors;

import com.markuvinicius.playground.avro.TwitterStatus;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class MessageForkProcessor extends AbstractProcessor{

    private final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
    private final Serde<TwitterStatus> valueSpecificAvroSerde = new SpecificAvroSerde<>();
    private final Logger logger = LoggerFactory.getLogger(MessageForkProcessor.class);


    public MessageForkProcessor(StreamsBuilder builder) {
        this.builder = builder;
        this.valueSpecificAvroSerde.configure(serdeConfig, false);
    }

    @Override
    public void execute() {

        KStream<String,TwitterStatus> source = builder
                .stream("twitter-normalized-messages", Consumed.with(Serdes.String(),valueSpecificAvroSerde));

        source
                .map( (key,value) -> new KeyValue<>(value.getLanguage(),value))
                .to("language-analysis-stream", Produced.with(Serdes.String(),valueSpecificAvroSerde));

//        source
//                .map( (key,value) -> new KeyValue<>(value.getCountry(),value))
//                .to("country-analysis-stream", Produced.with(Serdes.String(),valueSpecificAvroSerde));
    }
}
