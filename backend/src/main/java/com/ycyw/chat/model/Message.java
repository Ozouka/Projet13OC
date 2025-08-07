package com.ycyw.chat.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_message")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "from_user", nullable = true)
    private String from;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    
    @Column(name = "is_read", nullable = false)
    private Boolean isRead;
    
    public Message() {
        this.sentAt = LocalDateTime.now();
        this.isRead = false;
    }

    public Message(String from, String content) {
        this.from = from;
        this.content = content;
        this.sentAt = LocalDateTime.now();
        this.isRead = false;
        this.userId = from.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        if (from != null) {
            this.userId = from.hashCode();
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
} 