package com.olxListing.olxproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CustomerMessages")
public class CustomerMessages {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@OneToOne
	User_Entity sender;
	
	@OneToOne
	User_Entity receiver;
	
	String senderEmail;
	
	String receiverMail;
	
	String message;

	public CustomerMessages() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CustomerMessages(int id, User_Entity sender, User_Entity receiver, String senderEmail, String receiverMail,
			String message) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.senderEmail = senderEmail;
		this.receiverMail = receiverMail;
		this.message = message;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@JsonBackReference(value = "location-movement")
	public User_Entity getsender() {
		return sender;
	}


	public void setsender(User_Entity sender) {
		this.sender = sender;
	}

	@JsonBackReference
	public User_Entity getreceiver() {
		return receiver;
	}


	public void setreceiver(User_Entity receiver) {
		this.receiver = receiver;
	}


	public String getsenderEmail() {
		return senderEmail;
	}


	public void setsenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}


	public String getreceiverMail() {
		return receiverMail;
	}


	public void setreceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	

}
