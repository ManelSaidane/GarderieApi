package com.example.garderieapi.Controller;

import com.example.garderieapi.Service.MessageService;
import com.example.garderieapi.dto.MessageDTO;
import com.example.garderieapi.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
/*
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getConversationMessages(conversationId);
        if (messages == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/conversation/sender/{senderId}/receiver/{receiverId}")
    public ResponseEntity<List<Message>> getConversationMessagesUsingId(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<Message> messages = messageService.getConversationMessagesUsingId(senderId, receiverId);
        if (messages == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            Message message = messageService.createMessage(
                    Long.parseLong(messageDTO.getSenderId()),
                    Long.parseLong(messageDTO.getReceiverId()),
                    messageDTO.getContent()
            );
            return ResponseEntity.ok(message);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}

