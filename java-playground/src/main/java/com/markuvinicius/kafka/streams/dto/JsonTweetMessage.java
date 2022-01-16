package com.markuvinicius.kafka.streams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonTweetMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String userName;

    @JsonProperty
    private String timeStamp;

    @JsonProperty
    private String message;

    public JsonTweetMessage() {
        super();
    }

    public JsonTweetMessage(String userName, String timeStamp, String message) {
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonTweetMessage{" +
                "userName='" + userName + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
