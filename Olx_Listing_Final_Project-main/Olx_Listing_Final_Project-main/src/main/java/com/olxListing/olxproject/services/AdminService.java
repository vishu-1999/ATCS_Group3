package com.olxListing.olxproject.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olxListing.olxproject.entity.Admin;
import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.Login;
import com.olxListing.olxproject.entity.User_Entity;

@Service
public interface AdminService {

	public ResponseEntity<String> updateCustomer(String email, User_Entity user);

	public ResponseEntity<?> seeCustomers();

	public ResponseEntity<String> deactivateUser(String email);

	public String registerAdmin(Admin admin);

	public String loginAdmin(Login login);

	public ResponseEntity<?> getAllAdmin();

	public ResponseEntity<String> activateUser(String mail);

	public String logoutAdmin();

	public String removeListing(int id);

	public ResponseEntity<?> getActiveUsers();

	public ResponseEntity<?> getListingOfUser(String email);

	public ResponseEntity<?> getExpiredListing();

}
