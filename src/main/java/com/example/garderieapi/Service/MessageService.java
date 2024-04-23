package com.example.garderieapi.Service;


import com.example.garderieapi.Repository.ConversationRepository;
import com.example.garderieapi.Repository.MessageRepository;
import com.example.garderieapi.entity.Conversation;
import com.example.garderieapi.entity.Message;
import com.example.garderieapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
/*
    private final MessageRepository messageRepository;
   //* private final ConversationRepository conversationRepository;

    private  final UserService userService;
    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userService = userService;
    }

    public List<Message> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    public List<Message> getConversationMessagesUsingId(Long senderId, Long receiverId) {
        User sender = new User();
        sender.setId(senderId);
        Conversation conversation = conversationRepository.findByMembersContains(sender);
        if (conversation == null) {
            return null;
        }
        return messageRepository.findByConversationId(conversation.getId());
    }

    @Transactional
    public Message createMessage(Long senderId, Long receiverId, String content) {
        try {
            // Find or create conversation
            Conversation conversation = conversationRepository.findByMembers(senderId, receiverId)
                    .orElseGet(() -> {
                        Optional<User> sender = userService.getUserById(senderId);
                        Optional<User> receiver = userService.getUserById(receiverId);

                        if (sender.isPresent() && receiver.isPresent()) {
                            Conversation newConversation = new Conversation();
                            newConversation.setMembers(List.of(sender.get(), receiver.get()));
                            return conversationRepository.save(newConversation);
                        } else {
                            throw new IllegalArgumentException("Sender or receiver not found");
                        }
                    });

            // Create message
            Message message = new Message(senderId, receiverId, content, conversation.getId());
            return messageRepository.save(message);
        } catch (Exception e) {
            // Handle error
            System.err.println("Failed to create message: " + e.getMessage());
            throw new RuntimeException("Failed to create message", e);
        }
    }

*/
}
