package com.markuvinicius.kafka.streams.processors;

import org.apache.kafka.streams.StreamsBuilder;

public abstract class AbstractProcessor {
    protected StreamsBuilder builder;
    public abstract void execute();
}
