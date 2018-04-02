package com.opendebate.messenger.persistence.persistence.discussion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opendebate.messenger.common.Discussion;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Discussion")
@NoArgsConstructor
public class MutableDiscussion {

    @Id
    private int id;
    private String topic;

    @JsonCreator
    public MutableDiscussion(@JsonProperty("topic") String topic) {
        this.topic = topic;
    }

    public Discussion convertToDiscussion() {
        return new Discussion(getId(), getTopic());
    }
}
