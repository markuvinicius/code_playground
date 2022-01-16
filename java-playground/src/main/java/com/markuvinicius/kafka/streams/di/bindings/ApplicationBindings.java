package com.markuvinicius.kafka.streams.di.bindings;

import com.google.inject.AbstractModule;
import org.apache.kafka.streams.StreamsBuilder;

public class ApplicationBindings extends AbstractModule {

    @Override
    protected void configure() {
        //bind(StreamsBuilder.class).to(StreamsBuilder.class);
    }
}
