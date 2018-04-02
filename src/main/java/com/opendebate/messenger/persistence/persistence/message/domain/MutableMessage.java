package com.opendebate.messenger.persistence.persistence.message.domain;

import com.opendebate.messenger.common.Side;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Message")
public class MutableMessage {
    @Id
    private Integer id;
    private Integer discussionId;
    private String message;
    private Side side;
}
