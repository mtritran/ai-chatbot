package com.manhtri.ai_chatbot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String role;

    @Column(columnDefinition = "TEXT")
    String content;

    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    Conversation conversation;
}
