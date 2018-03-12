package com.opendebate.messenger.discussion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Discussion {
    private String topic;

    @JsonCreator
    public Discussion(@JsonProperty("topic") String topic) {
        this.topic = topic;
    }

}
