package com.opendebate.messenger.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Discussion {
    private final int id;
    private final String topic;
}
