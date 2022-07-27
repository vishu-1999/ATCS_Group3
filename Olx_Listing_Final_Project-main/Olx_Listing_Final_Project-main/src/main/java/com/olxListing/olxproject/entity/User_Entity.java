package com.olxListing.olxproject.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "User_Register", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mail"})      
})

public class User_Entity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank
	private String name;
	private String last_name;
	private int contact_No;
	@NotBlank
	private String mail;
	@NotBlank
	private String password;
	private boolean isLoggedIn = false;
	private boolean isActivate = true;
	
	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Listing> listings;
	
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Bookmark> bookmarks;
	
	public User_Entity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getContact_No() {
		return contact_No;
	}

	public void setContact_No(int contact_No) {
		this.contact_No = contact_No;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	

	@JsonBackReference
	public List<Listing> getListings() {
		return listings;
	}

	public void setListings(List<Listing> listings) {
		this.listings = listings;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	

	public boolean isActivate() {
		return isActivate;
	}

	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}


	public User_Entity(int id, String name, String last_name, int contact_No, String mail, String password,
			boolean isLoggedIn, boolean isActivate, List<Listing> listings) {
		super();
		this.id = id;
		this.name = name;
		this.last_name = last_name;
		this.contact_No = contact_No;
		this.mail = mail;
		this.password = password;
		this.isLoggedIn = isLoggedIn;
		this.isActivate = isActivate;
		this.listings = listings;
	}

	@Override
	public String toString() {
		return "User_Entity [id=" + id + ", name=" + name + ", last_name=" + last_name + ", contact_No=" + contact_No
				+ ", mail=" + mail + ", password=" + password + ", isLoggedIn=" + isLoggedIn + ", isActivate="
				+ isActivate + ", listings=" + listings + "]";
	}


}
