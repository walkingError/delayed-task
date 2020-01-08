package com.jito.delayedtask;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.UUID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class TaskWorker {

    private final DelayedTaskService delayedTaskService;

    @PostConstruct
    private void init() throws JsonProcessingException {
        delayedTaskService.add(Task.builder()
                .identifier(UUID.randomUUID().toString())
                .createdTimeMilliSeconds(Calendar.getInstance().getTimeInMillis())
                .delayTimeMilliSeconds(10000)
                .json("json data")
                .build());
    }

    @Scheduled(fixedDelay = 1000)
    private void polling() {
        log.info("polling");
        delayedTaskService.execute();
    }

}
