// Corrected MessageDTO class
package com.example.garderieapi.dto;

import com.example.garderieapi.entity.Message;

public class MessageDTO {

    private String senderId;
    private String receiverId;
    private String content;
    private String senderSocketId;
    private String receiverSocketId;

    public MessageDTO() {
    }

    public MessageDTO(String senderId, Message message) {
        this.senderId = senderId;
        this.receiverId = message.getReceiverId().toString();
        this.content = message.getContent();
        // You might need to set other fields as needed
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderSocketId() {
        return senderSocketId;
    }

    public void setSenderSocketId(String senderSocketId) {
        this.senderSocketId = senderSocketId;
    }

    public String getReceiverSocketId() {
        return receiverSocketId;
    }

    public void setReceiverSocketId(String receiverSocketId) {
        this.receiverSocketId = receiverSocketId;
    }
}
