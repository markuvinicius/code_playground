package com.markuvinicius.kafka.streams.processors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

public class TextMessageProcessor extends AbstractProcessor {

    public TextMessageProcessor(StreamsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void execute() {
        KStream<String, String> source = builder
                .stream("twitter-input-messages", Consumed.with(Serdes.String(), Serdes.String()));
                //.peek((key,value) -> System.out.println(value));

        source.to("twitter-stage-messages", Produced.with(Serdes.String(), Serdes.String()));
    }

}

