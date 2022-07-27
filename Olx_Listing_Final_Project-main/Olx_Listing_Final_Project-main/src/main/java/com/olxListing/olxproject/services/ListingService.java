package com.olxListing.olxproject.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.User_Entity;

@Service
public interface ListingService {
	
	public ResponseEntity<String> addListing(Listing listing);
	
	public ResponseEntity<?> displayListings();

	public ResponseEntity<?> displayContactDetails(int id);

	public Listing updateListing(Listing listing);

	public ResponseEntity<?> searchUsingCategory(String category);

	public ResponseEntity<?> searchUsingLocation(String city);

	public ResponseEntity<?> searchUsingPrice(int price);

	public ResponseEntity<?> sortListings();

}
