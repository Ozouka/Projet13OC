package com.ycyw.chat.controller;

import com.ycyw.chat.model.Message;
import com.ycyw.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    
    @Autowired
    private MessageService messageService;
    
    
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/messages/user/{from}")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable String from) {
        List<Message> messages = messageService.getMessagesByUser(from);
        return ResponseEntity.ok(messages);
    }
} 