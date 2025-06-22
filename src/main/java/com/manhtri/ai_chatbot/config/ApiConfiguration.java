package com.manhtri.ai_chatbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiConfiguration {

    @Value("${openrouter.api.key}")
    String apiKey;

    @Value("${openrouter.model}")
    String model;

    @Value("${openrouter.api.url}")
    String apiUrl;

    @Value("${openrouter.timeout:30}")
    int timeoutSeconds;

    @Value("${openrouter.referer:https://yourapp.com}")
    String referer;

    @Value("${openrouter.title:Flutter_AI_Chatbot}")
    String title;
}
