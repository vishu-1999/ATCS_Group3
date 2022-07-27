package com.olxListing.olxproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "AdminMessages")
public class AdminMessages {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@OneToOne
	Admin admin;
	
	@OneToOne
	User_Entity user;
	
	String adminMail;
	
	String custMail;
	
	String message;
	
	

	public AdminMessages() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public AdminMessages(int id, Admin admin, User_Entity user, String adminMail, String custMail, String message) {
		super();
		this.id = id;
		this.admin = admin;
		this.user = user;
		this.adminMail = adminMail;
		this.custMail = custMail;
		this.message = message;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@JsonBackReference(value = "location-movement")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	@JsonBackReference
	public User_Entity getUser() {
		return user;
	}

	public void setUser(User_Entity user) {
		this.user = user;
	}

	public String getadminMail() {
		return adminMail;
	}

	public void setadminMail(String adminMail) {
		this.adminMail = adminMail;
	}

	public String getcustMail() {
		return custMail;
	}

	public void setcustMail(String custMail) {
		this.custMail = custMail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
