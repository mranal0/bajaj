package com.example.webhooksqlsolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WebhookSqlSolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookSqlSolverApplication.class, args);
    }
} 