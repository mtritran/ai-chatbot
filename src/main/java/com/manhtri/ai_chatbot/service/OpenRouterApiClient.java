package com.manhtri.ai_chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manhtri.ai_chatbot.config.ApiConfiguration;
import com.manhtri.ai_chatbot.dto.ApiMessage;
import com.manhtri.ai_chatbot.dto.MessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OpenRouterApiClient {

    final ApiConfiguration apiConfiguration;
    final OpenRouterResponseParser responseParser;
    final ObjectMapper objectMapper;
    final HttpClient httpClient = HttpClient.newHttpClient();
    
    public MessageResponse sendChatRequest(List<ApiMessage> messages) throws Exception {
        String payload = createRequestPayload(messages);

        log.info("Sending request to OpenRouter API with {} messages", messages.size());
        log.debug("API URL: {}", apiConfiguration.getApiUrl());
        log.debug("Model: {}", apiConfiguration.getModel());
        log.debug("Request Payload: {}", payload);

        HttpRequest request = buildHttpRequest(payload);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Received response with status: {}", response.statusCode());
        log.debug("Response Body: {}", response.body());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return responseParser.parseResponse(response.body());
        } else {
            throw new RuntimeException("API call failed with status code: " + response.statusCode() + ", body: " + response.body());
        }
    }
    
    private String createRequestPayload(List<ApiMessage> messages) throws Exception {
        Map<String, Object> requestBody = Map.of(
            "model", apiConfiguration.getModel(),
            "messages", messages
        );

        return objectMapper.writeValueAsString(requestBody);
    }
    
    private HttpRequest buildHttpRequest(String payload) throws Exception {
        return HttpRequest.newBuilder()
                .uri(new URI(apiConfiguration.getApiUrl()))
                .timeout(Duration.ofSeconds(apiConfiguration.getTimeoutSeconds()))
                .header("Authorization", "Bearer " + apiConfiguration.getApiKey())
                .header("Content-Type", "application/json")
                .header("HTTP-Referer", apiConfiguration.getReferer())
                .header("X-Title", apiConfiguration.getTitle())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
    }
}
