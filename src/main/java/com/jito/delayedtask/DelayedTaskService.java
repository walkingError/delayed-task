package com.jito.delayedtask;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DelayedTaskService {


    void add(Task task) throws JsonProcessingException;

    void execute();
}
