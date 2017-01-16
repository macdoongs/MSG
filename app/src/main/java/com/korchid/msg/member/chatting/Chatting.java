package com.korchid.msg.member.chatting;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class Chatting {
    public enum Type {MESSAGE, IMAGE}


    private int senderId;
    private int receiverId;
    private String senderNickname;
    private Type messageType;
    private String message;

    public Chatting(int senderId, int receiverId, String senderNickname, Type messageType, String message){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderNickname = senderNickname;
        this.messageType = messageType;
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public Type getMessageType() {
        return messageType;
    }

    public void setMessageType(Type messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
