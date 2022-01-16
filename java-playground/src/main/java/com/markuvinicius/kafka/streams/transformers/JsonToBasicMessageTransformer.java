package com.markuvinicius.kafka.streams.transformers;

import com.markuvinicius.kafka.streams.dto.JsonTweetMessage;
import com.markuvinicius.playground.avro.TweetMessage;

public class JsonToBasicMessageTransformer implements Transformer{
    @Override
    public Object transform(Object o) {
        JsonTweetMessage message = (JsonTweetMessage) o;
        return TweetMessage.newBuilder()
                .setUserName(message.getUserName())
                .setTimestamp(message.getTimeStamp())
                .setMessage(message.getMessage())
                .build();
    }
}
