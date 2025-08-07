package com.ycyw.chat.service;

import com.ycyw.chat.model.Message;
import com.ycyw.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    private static final java.util.Map<Integer, String> USER_MAPPING = java.util.Map.of(
        804821614, "Toto",
        -820333692, "Bob"
    );
    
    public Message saveMessage(Message message) {
        if (message.getFrom() != null && message.getUserId() == null) {
            message.setUserId(message.getFrom().hashCode());
        }
        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAllOrderBySentAtDesc();
        for (Message message : messages) {
            if (message.getFrom() == null && message.getUserId() != null) {
                String userName = USER_MAPPING.get(message.getUserId());
                if (userName != null) {
                    message.setFrom(userName);
                } else {
                    message.setFrom("Utilisateur " + message.getUserId());
                }
            }
        }
        return messages;
    }
    
    public Optional<Message> getMessageById(Integer id) {
        Optional<Message> messageOpt = messageRepository.findById(id);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            if (message.getFrom() == null && message.getUserId() != null) {
                String userName = USER_MAPPING.get(message.getUserId());
                if (userName != null) {
                    message.setFrom(userName);
                } else {
                    message.setFrom("Utilisateur " + message.getUserId());
                }
            }
        }
        return messageOpt;
    }
    
    public List<Message> getMessagesByUser(String from) {
        Integer userId = from.hashCode();
        List<Message> messages = messageRepository.findByUserIdOrderBySentAtDesc(userId);
        for (Message message : messages) {
            if (message.getFrom() == null && message.getUserId() != null) {
                String userName = USER_MAPPING.get(message.getUserId());
                if (userName != null) {
                    message.setFrom(userName);
                } else {
                    message.setFrom("Utilisateur " + message.getUserId());
                }
            }
        }
        return messages;
    }
}
