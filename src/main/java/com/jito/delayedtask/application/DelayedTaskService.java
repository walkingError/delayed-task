package com.jito.delayedtask.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jito.delayedtask.domain.Task;

public interface DelayedTaskService {


    void add(Task task) throws JsonProcessingException;

    void execute();
}
