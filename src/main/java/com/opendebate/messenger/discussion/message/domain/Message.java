package com.opendebate.messenger.discussion.message.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Message")
public class Message {
    @Id
    private Integer id;
    private Integer discussionId;
    private String message;
    private Side side;
}
