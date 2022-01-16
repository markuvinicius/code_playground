package com.markuvinicius.kafka.streams.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {

    private ObjectMapper mapper = new ObjectMapper();

    protected Class<T> getType() {
        return type;
    }

    private Class<T> type;

    public JsonDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (type == null) {
            type = (Class<T>) configs.get("type");
        }
    }



    @Override
    public T deserialize(String s, byte[] bytes) {
        T data = null;
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            data = mapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
