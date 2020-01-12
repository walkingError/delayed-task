package com.jito.delayedtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DelayedTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayedTaskApplication.class, args);
    }

}
