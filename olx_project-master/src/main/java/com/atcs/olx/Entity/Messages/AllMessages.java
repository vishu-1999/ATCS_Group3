package com.atcs.olx.Entity.Messages;


import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.atcs.olx.Entity.Authenticate.Register;

@Entity
@Table(name = "all_users_messages")
public class AllMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
  
    @Column
    private long receiverId;

    @Column
    private String message;

    @Column
    private LocalDateTime date =  LocalDateTime.now();

    public AllMessages() {
    }

    @ManyToOne(cascade = CascadeType.ALL)
   private Register register;


    public AllMessages( long receiverId, String message, LocalDateTime date, Register register) {
        this.receiverId = receiverId;
        this.message = message;
        this.date = date;
        this.register = register;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
