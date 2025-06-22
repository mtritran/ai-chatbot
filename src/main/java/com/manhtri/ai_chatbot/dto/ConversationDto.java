package com.manhtri.ai_chatbot.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationDto {
    String id;
    String title;
    LocalDateTime createdAt;
    List<MessageDto> messages;
    int messageCount;
    String lastMessage;
    LocalDateTime lastMessageAt;
}
