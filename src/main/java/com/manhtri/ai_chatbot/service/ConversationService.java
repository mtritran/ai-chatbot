package com.manhtri.ai_chatbot.service;

import com.manhtri.ai_chatbot.dto.ConversationDto;
import com.manhtri.ai_chatbot.dto.ConversationUpdateRequest;
import com.manhtri.ai_chatbot.dto.MessageDto;
import com.manhtri.ai_chatbot.entity.Conversation;
import com.manhtri.ai_chatbot.entity.Message;
import com.manhtri.ai_chatbot.repository.ConversationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ConversationService {
    final ConversationRepository conversationRepository;

    public Conversation getOrCreateConversation(String conversationId) {
        if (conversationId == null || conversationId.isEmpty()) {
            return createNewConversation();
        } else {
            return findConversationById(conversationId);
        }
    }

    public Conversation createNewConversation() {
        log.info("Creating new conversation");
        Conversation conversation = Conversation.builder()
                .title("New conversation")
                .createdAt(LocalDateTime.now())
                .build();

        return conversationRepository.save(conversation);
    }

    public Conversation findConversationById(String conversationId) {
        log.info("Finding conversation with ID: {}", conversationId);
        return conversationRepository.findById(UUID.fromString(conversationId))
                .orElseThrow(() -> new RuntimeException("Conversation not found with ID: " + conversationId));
    }

    public Conversation saveConversation(Conversation conversation) {
        log.debug("Saving conversation: {}", conversation.getId());
        return conversationRepository.save(conversation);
    }

    // CRUD Methods
    public List<ConversationDto> getAllConversations() {
        log.info("Getting all conversations");
        List<Conversation> conversations = conversationRepository.findAll();
        return conversations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ConversationDto getConversationDtoById(String conversationId) {
        log.info("Getting conversation DTO by ID: {}", conversationId);
        Conversation conversation = findConversationById(conversationId);
        return convertToDto(conversation);
    }

    public ConversationDto createNewConversationDto() {
        log.info("Creating new conversation DTO");
        Conversation conversation = createNewConversation();
        return convertToDto(conversation);
    }

    public ConversationDto updateConversation(String conversationId, ConversationUpdateRequest request) {
        log.info("Updating conversation {}: {}", conversationId, request.getTitle());
        Conversation conversation = findConversationById(conversationId);
        conversation.setTitle(request.getTitle());
        conversation = conversationRepository.save(conversation);
        return convertToDto(conversation);
    }

    public void deleteConversation(String conversationId) {
        log.info("Deleting conversation: {}", conversationId);
        Conversation conversation = findConversationById(conversationId);
        conversationRepository.delete(conversation);
    }

    private ConversationDto convertToDto(Conversation conversation) {
        ConversationDto dto = new ConversationDto();
        dto.setId(conversation.getId().toString());
        dto.setTitle(conversation.getTitle());
        dto.setCreatedAt(conversation.getCreatedAt());

        if (conversation.getMessages() != null) {
            List<MessageDto> messageDtos = conversation.getMessages().stream()
                    .map(this::convertMessageToDto)
                    .collect(Collectors.toList());
            dto.setMessages(messageDtos);
            dto.setMessageCount(messageDtos.size());

            if (!messageDtos.isEmpty()) {
                MessageDto lastMessage = messageDtos.get(messageDtos.size() - 1);
                dto.setLastMessage(lastMessage.getContent());
                dto.setLastMessageAt(lastMessage.getCreatedAt());
            }
        } else {
            dto.setMessageCount(0);
        }

        return dto;
    }

    private MessageDto convertMessageToDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId().toString());
        dto.setRole(message.getRole());
        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
}
