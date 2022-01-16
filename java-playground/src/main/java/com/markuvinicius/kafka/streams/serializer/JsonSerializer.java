package com.markuvinicius.kafka.streams.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, T t) {
        byte[] serializedValue = null;
        try{
            serializedValue = mapper.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
        }catch (JsonProcessingException e){
            throw new SerializationException("error parsing object");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializedValue;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        return this.serialize(topic,data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
