package com.olxListing.olxproject.entity;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "location")

public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String address;
	String city;
	String state;
	int pincode;
	
	@OneToOne(mappedBy = "location", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Listing listing;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Location(int id, String address, String city, String state, int pincode, Listing listing) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.listing = listing;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	@JsonBackReference
	public Listing getListing() {
		return listing;
	}


	public void setListing(Listing listing) {
		this.listing = listing;
	}
	
	

}
