package com.olxListing.olxproject.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;


@Entity
@Table(name = "listing")

public class Listing {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String name;
	int price;
	String description;
	String category;
	
	
	boolean isactivate = true;
	
	Calendar calendar = Calendar.getInstance();
	Date date = calendar.getTime();// print today's date\
	
	Date expiryDate;
			
	@OneToOne(cascade = CascadeType.ALL)
	Location location;
	
	
	@ManyToOne
	User_Entity userEntity;
	
	public Listing() {
		super();
		this.calendar.add(Calendar.MONTH, 2);
		this.expiryDate = calendar.getTime();
	}


	public Listing(int id, String name, int price, String description, String category, boolean isactivate, Date date,
			Date expiryDate, Location location, User_Entity userEntity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.isactivate = isactivate;
		this.date = date;
		this.expiryDate = expiryDate;
		this.location = location;
		this.userEntity = userEntity;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonBackReference(value = "location-movement")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonBackReference
	public User_Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(User_Entity userEntity) {
		this.userEntity = userEntity;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}



	public boolean isIsactivate() {
		return isactivate;
	}



	public void setIsactivate(boolean isactivate) {
		this.isactivate = isactivate;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}
