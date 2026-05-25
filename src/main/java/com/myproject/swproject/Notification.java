/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

import java.time.LocalDateTime;

public class Notification {

    private int notificationId;
    private int userId;
    private int itemId; 
    private NotificationType type;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;

    // ===== ENUMS =====

public enum NotificationType {
    item_approved,
    item_rejected,
    request_received,
    donation_in_progress,
    donation_completed,
    system
}


    public enum NotificationStatus {
        unread,
        read
    }

    // ===== CONSTRUCTORS =====

    public Notification() {
    }

    public Notification(int userId, Integer itemId, NotificationType type, String message) {
        this.userId = userId;
        this.itemId = itemId;
        this.type = type;
        this.message = message;
        this.status = NotificationStatus.unread;
    }
    
    public Notification(int notificationId, int userId, Integer itemId, NotificationType type,
                        String message, NotificationStatus status, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.itemId = itemId;
        this.type = type;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public boolean isUnread() {
        return this.status == NotificationStatus.unread;
    }

     /* @Override
  public String toString() {
        return "[" + status + "] " + type + ": " + message + 
               (itemId != null ? " (Item ID: " + itemId + ")" : "") +
               " | User ID: " + userId + 
               " | Created: " + createdAt;
    } */

    // ===== GETTERS AND SETTERS =====

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

