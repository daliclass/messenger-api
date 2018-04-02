package com.opendebate.messenger.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Message {
    private final Integer id;
    private final Integer discussionId;
    private final String message;
    private final Side side;
}
