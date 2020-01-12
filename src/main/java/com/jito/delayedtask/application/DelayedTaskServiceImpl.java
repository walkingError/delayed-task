package com.jito.delayedtask.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jito.delayedtask.domain.DelayedTaskRepository;
import com.jito.delayedtask.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@Slf4j
public class DelayedTaskServiceImpl implements DelayedTaskService {

    private final DelayedTaskRepository delayedTaskRepository;

    public DelayedTaskServiceImpl(DelayedTaskRepository delayedTaskRepository) {
        this.delayedTaskRepository = delayedTaskRepository;
    }

    @Override
    public void add(Task task) throws JsonProcessingException {
        delayedTaskRepository.add(task);
    }

    @Override
    public void execute() {
        delayedTaskRepository.zrange(1).iterator()
                .forEachRemaining(task -> {
                    log.info("task info : {}", task.toString());
                    long now = Calendar.getInstance().getTimeInMillis();
                    if (getComputedTime(task) <= now) {
                        log.info("execute now!");
                        try {
                            delayedTaskRepository.zrem(task);
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                        }
                    } else {
                        log.info("execute not yet");
                    }
                });
    }

    private long getComputedTime(Task task) {
        return task.getCreatedTimeMilliSeconds() + task.getDelayTimeMilliSeconds();
    }
}
