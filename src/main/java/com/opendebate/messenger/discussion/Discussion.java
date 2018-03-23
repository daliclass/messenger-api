package com.opendebate.messenger.discussion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Discussion")
@NoArgsConstructor
public class Discussion {

    @Id
    private int id;
    private String topic;

    @JsonCreator
    public Discussion(@JsonProperty("topic") String topic) {
        this.topic = topic;
    }
}
