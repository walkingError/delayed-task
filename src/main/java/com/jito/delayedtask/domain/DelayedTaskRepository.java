package com.jito.delayedtask.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jito.delayedtask.domain.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DelayedTaskRepository {

    private final ZSetOperations zSetOperations;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("{delayed-task-queue-key:DELAYED_TASK_QUEUE}")
    private String DELAYED_TASK_QUEUE;

    public DelayedTaskRepository(RedisTemplate redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void add(Task task) throws JsonProcessingException {
        String data = objectMapper.writeValueAsString(task);
        zSetOperations.add(DELAYED_TASK_QUEUE, data, task.getDelayTimeMilliSeconds());
    }

    public Set<Task> zrange(long count) {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        Set<String> tasks = zSetOperations.rangeByScore(DELAYED_TASK_QUEUE, 0, now, 0, count);
        if (null == tasks) {
            return Collections.emptySet();
        }
        return tasks.stream()
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Task.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        throw new RuntimeException("parsing fail to read from redis");
                    }
                })
                .collect(Collectors.toSet());
    }

    public void zrem(Task task) throws JsonProcessingException {
        String data = objectMapper.writeValueAsString(task);
        zSetOperations.remove(DELAYED_TASK_QUEUE, data);
    }
}
