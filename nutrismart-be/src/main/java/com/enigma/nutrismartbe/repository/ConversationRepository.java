package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
}
