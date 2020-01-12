package com.jito.delayedtask.application;

import com.jito.delayedtask.application.DelayedTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class QueuePollingWorker {

    private final DelayedTaskService delayedTaskService;

    @Scheduled(fixedDelay = 1000)
    private void polling() {
        log.info("polling");
        delayedTaskService.execute();
    }

}
