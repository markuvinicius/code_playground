package com.markuvinicius.kafka.streams;

import com.markuvinicius.kafka.streams.processors.*;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Application {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "twitter-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("schema.registry.url", "http://localhost:8081");

        StreamsBuilder builder = new StreamsBuilder();

        TextMessageProcessor messageProcessor = new TextMessageProcessor(builder);
        TwitterMessageNormalizeProcessor processor = new TwitterMessageNormalizeProcessor(builder);
        MessageForkProcessor forkProcessor = new MessageForkProcessor(builder);
        LanguageAggregateProcessor languageAggregateProcessor = new LanguageAggregateProcessor(builder);

        messageProcessor.execute();
        processor.execute();
        forkProcessor.execute();
        languageAggregateProcessor.execute();


        final Topology topology = builder.build();
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
