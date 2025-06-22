package com.manhtri.ai_chatbot.controller;

import com.manhtri.ai_chatbot.dto.ConversationDto;
import com.manhtri.ai_chatbot.dto.ConversationUpdateRequest;
import com.manhtri.ai_chatbot.entity.Conversation;
import com.manhtri.ai_chatbot.service.ConversationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@CrossOrigin(originPatterns = "*")
public class ConversationController {
    final ConversationService conversationService;
    
    // GET /api/conversations - Lấy tất cả conversations
    @GetMapping
    public ResponseEntity<List<ConversationDto>> getAllConversations() {
        try {
            log.info("Getting all conversations");
            List<ConversationDto> conversations = conversationService.getAllConversations();
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            log.error("Error getting conversations: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // GET /api/conversations/{id} - Lấy conversation theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getConversationById(@PathVariable String id) {
        try {
            log.info("Getting conversation by ID: {}", id);
            ConversationDto conversation = conversationService.getConversationDtoById(id);
            return ResponseEntity.ok(conversation);
        } catch (Exception e) {
            log.error("Error getting conversation {}: {}", id, e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    // POST /api/conversations - Tạo conversation mới
    @PostMapping
    public ResponseEntity<?> createConversation() {
        try {
            log.info("Creating new conversation");
            ConversationDto conversation = conversationService.createNewConversationDto();
            return ResponseEntity.ok(conversation);
        } catch (Exception e) {
            log.error("Error creating conversation: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    // PUT /api/conversations/{id} - Cập nhật conversation
    @PutMapping("/{id}")
    public ResponseEntity<?> updateConversation(@PathVariable String id, @RequestBody ConversationUpdateRequest request) {
        try {
            log.info("Updating conversation {}: {}", id, request.getTitle());
            ConversationDto conversation = conversationService.updateConversation(id, request);
            return ResponseEntity.ok(conversation);
        } catch (Exception e) {
            log.error("Error updating conversation {}: {}", id, e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    // DELETE /api/conversations/{id} - Xóa conversation
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConversation(@PathVariable String id) {
        try {
            log.info("Deleting conversation: {}", id);
            conversationService.deleteConversation(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Conversation deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting conversation {}: {}", id, e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
