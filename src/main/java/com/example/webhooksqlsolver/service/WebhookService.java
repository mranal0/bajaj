package com.example.webhooksqlsolver.service;

import com.example.webhooksqlsolver.model.WebhookRequest;
import com.example.webhooksqlsolver.model.WebhookResponse;
import com.example.webhooksqlsolver.model.SolutionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class WebhookService {
    
    private final RestTemplate restTemplate;
    private final String generateWebhookUrl;
    private final String userName;
    private final String userEmail;
    private final String userRegNo;
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    
    public WebhookService(
            RestTemplate restTemplate,
            @Value("${webhook.generate.url}") String generateWebhookUrl,
            @Value("${app.user.name}") String userName,
            @Value("${app.user.email}") String userEmail,
            @Value("${app.user.regno}") String userRegNo) {
        this.restTemplate = restTemplate;
        this.generateWebhookUrl = generateWebhookUrl;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRegNo = userRegNo;
    }
    
    public WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest();
        request.setName(userName);
        request.setRegNo(userRegNo);
        request.setEmail(userEmail);
        
        logger.info("Sending webhook generation request for user: {}", userName);
        WebhookResponse response = restTemplate.postForObject(generateWebhookUrl, request, WebhookResponse.class);
        logger.info("Received webhook response with URL: {}", response.getWebhook());
        
        return response;
    }
    
    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        
        SolutionRequest request = new SolutionRequest();
        request.setFinalQuery(finalQuery);
        
        HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
        logger.info("Submitting solution to webhook URL: {}", webhookUrl);
        restTemplate.postForObject(webhookUrl, entity, Void.class);
        logger.info("Solution submitted successfully");
    }
    
    public String getUserRegNo() {
        return userRegNo;
    }
} 