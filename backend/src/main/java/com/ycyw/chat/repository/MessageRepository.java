package com.ycyw.chat.repository;

import com.ycyw.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    @Query("SELECT m FROM Message m ORDER BY m.sentAt DESC")
    List<Message> findAllOrderBySentAtDesc();
    
    List<Message> findByUserIdOrderBySentAtDesc(Integer userId);
}
