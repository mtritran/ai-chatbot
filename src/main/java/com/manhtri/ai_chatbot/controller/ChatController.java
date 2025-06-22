package com.manhtri.ai_chatbot.controller;

import com.manhtri.ai_chatbot.dto.MessageRequest;
import com.manhtri.ai_chatbot.dto.MessageResponse;
import com.manhtri.ai_chatbot.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@CrossOrigin(originPatterns = "*")
public class ChatController {
    final ChatService chatService;

    @PostMapping
    public ResponseEntity<?> chatResponse(@RequestBody MessageRequest messageRequest) {
        try {
            log.info("Received chat request: {}", messageRequest.getMessage());
            log.info("Using Qwen model via OpenRouter");
            MessageResponse response = chatService.chatResponse(messageRequest);
            log.info("Response generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing request: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
