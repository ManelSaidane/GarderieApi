// Corrected Message class
package com.example.garderieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long receiverId;
    private String content;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public Message(Long senderId, Long receiverId, String content, Long conversationId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.date = new Date(); // Set current date
        this.conversation = new Conversation();
        this.conversation.setId(conversationId);
    }
}
