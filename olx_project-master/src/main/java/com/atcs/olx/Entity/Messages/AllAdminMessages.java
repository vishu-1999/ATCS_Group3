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


import com.atcs.olx.Entity.Authenticate.Admin_Register;

@Entity
@Table(name = "all_admin_messages")
public class AllAdminMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String receiverUserId ;

    @Column
    private String receiverAdminId;

    @Column
    private String message;

    @Column
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
   private Admin_Register adminRegister;


    public AllAdminMessages() {
    }

    public AllAdminMessages(String receiverUserId, String receiverAdminId,  String message, LocalDateTime date, Admin_Register adminRegister) {
        this.receiverUserId = receiverUserId;
        this.receiverAdminId = receiverAdminId;
        this.message = message;
        this.date = date;
        this.adminRegister = adminRegister;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getReceiverAdminId() {
        return receiverAdminId;
    }

    public void setReceiverAdminId(String receiverAdminId) {
        this.receiverAdminId = receiverAdminId;
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

    public Admin_Register getAdminRegister() {
        return adminRegister;
    }

    public void setAdminRegister(Admin_Register adminRegister) {
        this.adminRegister = adminRegister;
    }

}
