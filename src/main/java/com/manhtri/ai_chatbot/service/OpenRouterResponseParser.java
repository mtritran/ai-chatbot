package com.manhtri.ai_chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manhtri.ai_chatbot.dto.MessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OpenRouterResponseParser {

    final ObjectMapper objectMapper;
    
    public MessageResponse parseResponse(String responseBody) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        MessageResponse messageResponse = new MessageResponse();
        // Don't set id from OpenRouter - will be set by ChatService
        messageResponse.setObject(jsonNode.path("object").asText());
        messageResponse.setCreated(jsonNode.path("created").asLong());
        messageResponse.setModel(jsonNode.path("model").asText());
        messageResponse.setProvider("OpenRouter");
        
        // Parse choices
        JsonNode choicesNode = jsonNode.path("choices");
        if (choicesNode.isArray() && choicesNode.size() > 0) {
            JsonNode firstChoice = choicesNode.get(0);
            
            MessageResponse.Choice choice = new MessageResponse.Choice();
            choice.setIndex(firstChoice.path("index").asInt());
            choice.setFinish_reason(firstChoice.path("finish_reason").asText());
            
            // Parse message
            JsonNode messageNode = firstChoice.path("message");
            MessageResponse.Message message = new MessageResponse.Message();
            message.setRole(messageNode.path("role").asText());
            message.setContent(messageNode.path("content").asText());
            
            choice.setMessage(message);
            messageResponse.setChoices(Collections.singletonList(choice));
        }
        
        return messageResponse;
    }
}
