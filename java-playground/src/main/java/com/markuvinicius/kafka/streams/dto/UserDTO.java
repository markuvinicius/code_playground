package com.markuvinicius.kafka.streams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = Long.MAX_VALUE;

    @JsonProperty
    private Long id;
    @JsonProperty
    private Date createdAt;
    @JsonProperty
    private String lang;
    @JsonProperty
    private String email;
    @JsonProperty
    private Integer followersCount;
    @JsonProperty
    private String name;
    @JsonProperty
    private String screenName;

    public UserDTO(Long id, Date createdAt, String lang, String email, Integer followersCount, String name, String screenName) {
        this.id = id;
        this.createdAt = createdAt;
        this.lang = lang;
        this.email = email;
        this.followersCount = followersCount;
        this.name = name;
        this.screenName = screenName;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public static final class Builder {
        private Long id;
        private Date createdAt;
        private String lang;
        private String email;
        private Integer followersCount;
        private String name;
        private String screenName;

        private Builder() {
        }

        public static Builder anUserDTO() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withLang(String lang) {
            this.lang = lang;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "id=" + id +
                    ", createdAt=" + createdAt +
                    ", lang='" + lang + '\'' +
                    ", email='" + email + '\'' +
                    ", followersCount=" + followersCount +
                    ", name='" + name + '\'' +
                    ", screenName='" + screenName + '\'' +
                    '}';
        }

        public Builder withFollowersCount(Integer followersCount) {
            this.followersCount = followersCount;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withScreenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setCreatedAt(createdAt);
            userDTO.setLang(lang);
            userDTO.setEmail(email);
            userDTO.setFollowersCount(followersCount);
            userDTO.setName(name);
            userDTO.setScreenName(screenName);
            return userDTO;
        }
    }
}
