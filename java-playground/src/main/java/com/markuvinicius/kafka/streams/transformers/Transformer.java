package com.markuvinicius.kafka.streams.transformers;

public interface Transformer<A,B> {
    public B transform(A a);
}
