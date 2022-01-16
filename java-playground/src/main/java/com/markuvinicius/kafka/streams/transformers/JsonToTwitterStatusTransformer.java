package com.markuvinicius.kafka.streams.transformers;

import com.markuvinicius.kafka.streams.dto.StatusDTO;
import com.markuvinicius.playground.avro.TwitterStatus;
import com.markuvinicius.playground.avro.TwitterUser;

public class JsonToTwitterStatusTransformer implements Transformer<StatusDTO, TwitterStatus> {
    @Override
    public TwitterStatus transform(StatusDTO statusDTO) {
        TwitterUser user = TwitterUser.newBuilder()
                .setId(statusDTO.getUser().getId())
                .setName(statusDTO.getUser().getName())
                .setCreatedAt(statusDTO.getUser().getCreatedAt().toString())
                .setFollowersCount(statusDTO.getUser().getFollowersCount())
                .setScreenName(statusDTO.getUser().getScreenName())
                .setEmail(statusDTO.getUser().getEmail())
                .build();

        TwitterStatus status = TwitterStatus.newBuilder()
                .setId(statusDTO.getId())
                .setCreatedAt(statusDTO.getCreatedAt().toString())
                .setFavoriteCount(statusDTO.getFavoriteCount())
                .setRetweetCount(statusDTO.getRetweetCount())
                .setText(statusDTO.getText())
                .setLanguage(statusDTO.getLang())
                .setUser(user)
                .build();

        return status;
    }
}
