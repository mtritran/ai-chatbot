package com.manhtri.ai_chatbot.service;

import com.manhtri.ai_chatbot.dto.ApiMessage;
import com.manhtri.ai_chatbot.dto.MessageRequest;
import com.manhtri.ai_chatbot.dto.MessageResponse;
import com.manhtri.ai_chatbot.entity.Conversation;
import com.manhtri.ai_chatbot.entity.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatService {
    final ConversationService conversationService;
    final MessageService messageService;
    final OpenRouterApiClient openRouterApiClient;

    public MessageResponse chatResponse(MessageRequest messageRequest) throws Exception {
        log.info("Processing chat request for message: {}", messageRequest.getMessage());

        // Step 1: Get or create conversation
        Conversation conversation = conversationService.getOrCreateConversation(messageRequest.getConversationId());
        log.info("Using conversation ID: {}", conversation.getId());

        // Step 2: Save user message
        messageService.saveUserMessage(conversation, messageRequest.getMessage());

        // Step 3: Load conversation history
        List<Message> messageHistory = messageService.getConversationHistory(conversation);
        List<ApiMessage> apiMessages = convertToApiMessages(messageHistory);

        // Step 4: Send request to OpenRouter API with full context
        MessageResponse response = openRouterApiClient.sendChatRequest(apiMessages);

        // Step 5: Save assistant response
        if (response.getChoices() != null && !response.getChoices().isEmpty()) {
            String assistantContent = response.getChoices().get(0).getMessage().getContent();
            messageService.saveAssistantMessage(conversation, assistantContent);
        }

        // Step 6: Set conversationId and id in response
        response.setConversationId(conversation.getId().toString());
        response.setId(conversation.getId().toString());

        log.info("Chat response generated successfully");
        return response;
    }

    private List<ApiMessage> convertToApiMessages(List<Message> messageHistory) {
        List<ApiMessage> apiMessages = new ArrayList<>();
        for (Message message : messageHistory) {
            apiMessages.add(new ApiMessage(message.getRole(), message.getContent()));
        }
        return apiMessages;
    }

}
