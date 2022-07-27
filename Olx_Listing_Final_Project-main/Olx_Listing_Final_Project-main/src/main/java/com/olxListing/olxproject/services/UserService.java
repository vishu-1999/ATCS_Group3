package com.olxListing.olxproject.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olxListing.olxproject.entity.Bookmark;
import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.User_Entity;



@Service
public interface UserService {
	public ResponseEntity<String> registerUser(User_Entity b);

	public ResponseEntity<?> display();

	public User_Entity updateUser(User_Entity b);
	
	public ResponseEntity<String> deleteUserEntity(int id);
	
	public ResponseEntity<?> displayUserListing(int id);

	public ResponseEntity<String> deactivateListing(String email, int id);

	public ResponseEntity<String> addBookmark(Bookmark bookmark);
	
}
