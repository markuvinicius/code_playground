package com.markuvinicius.kafka.streams.processors;

import com.markuvinicius.kafka.streams.dto.StatusDTO;
import com.markuvinicius.kafka.streams.serializer.JsonDeserializer;
import com.markuvinicius.kafka.streams.serializer.JsonSerializer;
import com.markuvinicius.kafka.streams.transformers.JsonToTwitterStatusTransformer;
import com.markuvinicius.playground.avro.TwitterStatus;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.KStreamAggregate;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class LanguageAggregateProcessor extends AbstractProcessor{

    private final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
    private final Serde<TwitterStatus> valueSpecificAvroSerde = new SpecificAvroSerde<>();
    private final Logger logger = LoggerFactory.getLogger(LanguageAggregateProcessor.class);


    public LanguageAggregateProcessor(StreamsBuilder builder) {
        this.builder = builder;
        this.valueSpecificAvroSerde.configure(serdeConfig, false);
    }

    @Override
    public void execute() {

        KStream<String,TwitterStatus> source = builder.stream("language-analysis-stream", Consumed.with(Serdes.String(), valueSpecificAvroSerde));

        KGroupedStream<String, TwitterStatus> groupedStream = source.groupByKey(Grouped.with(Serdes.String(), valueSpecificAvroSerde));

        KTable<String,Long> table = groupedStream.aggregate(
                () -> 0L, //Initializer,
                (aggKey, newValue, aggValue) -> aggValue+1,
                Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("aggregated-stream-store" /* state store name */)
                        .withKeySerde(Serdes.String()) /* key serde */
                        .withValueSerde(Serdes.Long())); /* serde for aggregate value */


        table.toStream()
                .peek((key,value) -> {
                    System.out.println("key: " + key + " - value: " + value);
                })
                .to("language-count-table", Produced.with(Serdes.String(),Serdes.Long()));
    }
}
