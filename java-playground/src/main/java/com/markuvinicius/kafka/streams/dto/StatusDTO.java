package com.markuvinicius.kafka.streams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class StatusDTO implements Serializable {
    private static final long serialVersionUID = Long.MAX_VALUE;

    @JsonProperty private Long id;
    @JsonProperty private String lang;
    @JsonProperty private Date createdAt;
    @JsonProperty private Integer favoriteCount;
    @JsonProperty private Integer retweetCount;
    @JsonProperty private String text;
    @JsonProperty private UserDTO user;
    @JsonProperty private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public StatusDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static final class Builder {
        private Long id;
        private String lang;
        private Date createdAt;
        private Integer favoriteCount;
        private Integer retweetCount;
        private String text;
        private UserDTO user;
        private String location;

        private Builder() {
        }

        public static Builder aStatusDTO() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withLang(String lang) {
            this.lang = lang;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withFavoriteCount(Integer favoriteCount) {
            this.favoriteCount = favoriteCount;
            return this;
        }

        public Builder withRetweetCount(Integer retweetCount) {
            this.retweetCount = retweetCount;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withUser(UserDTO user) {
            this.user = user;
            return this;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public StatusDTO build() {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setId(id);
            statusDTO.setLang(lang);
            statusDTO.setCreatedAt(createdAt);
            statusDTO.setFavoriteCount(favoriteCount);
            statusDTO.setRetweetCount(retweetCount);
            statusDTO.setText(text);
            statusDTO.setUser(user);
            statusDTO.setLocation(location);
            return statusDTO;
        }
    }
}
