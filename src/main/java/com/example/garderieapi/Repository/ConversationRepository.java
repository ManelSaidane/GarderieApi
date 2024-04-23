package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Conversation;
import com.example.garderieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findByMembersContains(User user);

 //*   Optional<Conversation> findByMembers(Long senderId, Long receiverId);
}