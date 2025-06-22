package com.manhtri.ai_chatbot.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MessageResponse {
    String id;
    String object;
    long created;
    String model;
    String provider;
    String conversationId;
    List<Choice> choices;

    public MessageResponse(String content) {
        this.id = java.util.UUID.randomUUID().toString();
        this.object = "chat.completion";
        this.created = System.currentTimeMillis() / 1000;
        this.model = "unknown";
        this.provider = "unknown";

        Message message = new Message();
        message.setRole("assistant");
        message.setContent(content);

        Choice choice = new Choice();
        choice.setIndex(0);
        choice.setMessage(message);
        choice.setFinish_reason("stop");

        this.choices = List.of(choice);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Choice {
        int index;
        Message message;
        String finish_reason;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Message {
        String role;
        String content;
    }
}
