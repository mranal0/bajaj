package com.example.webhooksqlsolver.component;

import com.example.webhooksqlsolver.model.WebhookResponse;
import com.example.webhooksqlsolver.service.WebhookService;
import com.example.webhooksqlsolver.service.SqlSolutionService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Slf4j
public class WebhookInitializer {
    
    private final WebhookService webhookService;
    private final SqlSolutionService sqlSolutionService;
    private static final Logger logger = LoggerFactory.getLogger(WebhookInitializer.class);
    
    public WebhookInitializer(WebhookService webhookService, SqlSolutionService sqlSolutionService) {
        this.webhookService = webhookService;
        this.sqlSolutionService = sqlSolutionService;
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            logger.info("Starting webhook initialization process");
            
            // Step 1: Generate webhook
            WebhookResponse response = webhookService.generateWebhook();
            logger.info("Successfully generated webhook");
            
            // Step 2: Get the SQL solution based on registration number
            String regNo = webhookService.getUserRegNo();
            String finalQuery = sqlSolutionService.getSolutionQuery(regNo);
            logger.info("Generated SQL solution for registration number: {}", regNo);
            
            // Step 3: Submit the solution
            webhookService.submitSolution(response.getWebhook(), response.getAccessToken(), finalQuery);
            logger.info("Successfully submitted solution to webhook");
            
        } catch (Exception e) {
            logger.error("Error during webhook initialization", e);
            throw new RuntimeException("Failed to process webhook initialization", e);
        }
    }
} 