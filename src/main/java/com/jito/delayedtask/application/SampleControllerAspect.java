package com.jito.delayedtask.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jito.delayedtask.domain.Task;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.UUID;


@Aspect
@Component
@RequiredArgsConstructor
public class SampleControllerAspect {

    private final DelayedTaskService delayedTaskService;

    @AfterReturning("execution(* com.jito.delayedtask.interfaces.SampleController.test(String)) && args(param)")
    public void registerQueue(String param) throws JsonProcessingException {
        String data = param;

        delayedTaskService.add(Task.builder()
                .identifier(UUID.randomUUID().toString())
                .createdTimeMilliSeconds(Calendar.getInstance().getTimeInMillis())
                .delayTimeMilliSeconds(10000)
                .json(data)
                .build());

    }

}
