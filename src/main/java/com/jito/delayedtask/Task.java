package com.jito.delayedtask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private String identifier;

    private String json;

    private long createdTimeMilliSeconds;

    private long delayTimeMilliSeconds;

}

