package com.manhtri.ai_chatbot.service;

import com.manhtri.ai_chatbot.entity.Conversation;
import com.manhtri.ai_chatbot.entity.Message;
import com.manhtri.ai_chatbot.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageService {
    final MessageRepository messageRepository;
    
    public Message saveUserMessage(Conversation conversation, String content) {
        log.debug("Saving user message for conversation: {}", conversation.getId());
        Message message = new Message();
        message.setConversation(conversation);
        message.setRole("user");
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        
        return messageRepository.save(message);
    }
    
    public Message saveAssistantMessage(Conversation conversation, String content) {
        log.debug("Saving assistant message for conversation: {}", conversation.getId());
        Message message = new Message();
        message.setConversation(conversation);
        message.setRole("assistant");
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        
        return messageRepository.save(message);
    }
    
    public List<Message> getConversationHistory(Conversation conversation) {
        log.debug("Loading conversation history for: {}", conversation.getId());
        return messageRepository.findByConversationOrderByCreatedAtAsc(conversation);
    }
}
