package com.olxListing.olxproject.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.olxListing.olxproject.entity.Bookmark;
import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.User_Entity;
import com.olxListing.olxproject.repository.Bookmark_Repo;
import com.olxListing.olxproject.repository.Listing_Repo;
import com.olxListing.olxproject.repository.User_Repo;
import com.olxListing.olxproject.services.UserService;



@Component
public class UserServiceImpli implements UserService {
	
	@Autowired
	private User_Repo userRepo;
	
	@Autowired
	private Listing_Repo listingRepo;
	
	@Autowired Bookmark_Repo bookmarkRepo;
	
	public ResponseEntity<String> registerUser(User_Entity b) {
		try {
			
				userRepo.save(b);
				return  new ResponseEntity<String>("Registration Successful",HttpStatus.OK);
           
			
		}catch(Exception e) {
			return new ResponseEntity<String>("Enter details correctly",HttpStatus.BAD_REQUEST);
		}
		 
		
	}
	
	public ResponseEntity<?> display()
	{
        try {
        	if(!userRepo.findAll().isEmpty())
        		return ResponseEntity.ok(userRepo.findAll());
        	else 
        		return new ResponseEntity<String>("No customers availabe....",HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
        	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
	}

	@Override
	public User_Entity updateUser(User_Entity b) {
		return userRepo.save(b);
	}

	@Override
	public ResponseEntity<String> deleteUserEntity(int id) {
		try {
			userRepo.deleteById(id);
			return new ResponseEntity<String>( "User is deleted Successfully!",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("id is invalid.....",HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> displayUserListing(int id) {
		try {
			User_Entity user = userRepo.findById(id).get();
			if(!user.getListings().isEmpty())
				return new ResponseEntity<List<Listing>>(user.getListings(),HttpStatus.OK);
			else
				return new ResponseEntity<String>("No listings available for the customer.....",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("Invalid id",HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<String> deactivateListing(String email, int id) {
		try {
			User_Entity user = userRepo.findBymail(email);
			if(user.isActivate() && user.isLoggedIn()) {
				Listing listing = listingRepo.findById(id).get();
				listing.setIsactivate(false);
				listingRepo.save(listing);
				
				return new ResponseEntity<String>("Listing is deactivated successfully!",HttpStatus.OK);
			}
			return new ResponseEntity<String>("User is not logged In",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("Invalid email or id",HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<String> addBookmark(Bookmark bookmark) {
		try {
			int user_id = bookmark.getUserId().getId();
			User_Entity user = userRepo.findById(user_id).get();
			
			if(user.isActivate() && user.isLoggedIn()) {
				bookmarkRepo.save(bookmark);
				return new ResponseEntity<String>("Bookmark is added successfully!",HttpStatus.OK);
			}
			return new ResponseEntity<String>("User is not logged In",HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Invalid details", HttpStatus.BAD_REQUEST);
		}
		
		
	}
		

}
