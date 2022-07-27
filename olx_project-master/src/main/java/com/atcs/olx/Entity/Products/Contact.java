package com.atcs.olx.Entity.Products;

public class Contact {

    private String firstname;

	private String lastname;
	
	private String email;
	
	private String phone_number;
	
    public Contact(String firstname, String lastname, String email, String phone_number) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone_number = phone_number;
	}

	public Contact() {
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	
	
}


