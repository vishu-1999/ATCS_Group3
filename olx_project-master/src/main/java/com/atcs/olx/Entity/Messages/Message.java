package com.atcs.olx.Entity.Messages;

public class Message {
    
    private String email;
    private String message;

    public Message() {
    }
    public Message(String email, String message) {
        this.email = email;
        this.message = message;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
