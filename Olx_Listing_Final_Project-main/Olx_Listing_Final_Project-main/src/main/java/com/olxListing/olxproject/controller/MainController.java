package com.olxListing.olxproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxListing.olxproject.entity.Bookmark;
import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.User_Entity;
import com.olxListing.olxproject.services.UserService;

@RestController

@RequestMapping("/customer")
public class MainController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> userRegisterd(@RequestBody User_Entity b )
	{
		return userService.registerUser(b);
		
	}

	
	@PostMapping("/bookmarkListing")
	public ResponseEntity<String> addBookmark(@RequestBody Bookmark bookmark) {
		return userService.addBookmark(bookmark);
	}
	
	@GetMapping("/displayAll")
	public ResponseEntity<?> display()
	{
		return userService.display();
	}
	
	@GetMapping("/displayListings/{id}")
	public ResponseEntity<?> displayUserListings(@PathVariable("id") int id){
		return userService.displayUserListing(id);
	}

	
	@PutMapping("/deactivateListing/{email}/{id}")
	public ResponseEntity<String> deactivateUser(@PathVariable("email") String email, @PathVariable("id") int id) {
		return userService.deactivateListing(email, id);
	}
	
	@DeleteMapping("/register/{id}")
	public ResponseEntity<String> deleteUserEntity(@PathVariable("id") int id) {
		return userService.deleteUserEntity(id);	
	}
}
