package com.olxListing.olxproject.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olxListing.olxproject.entity.AdminMessages;
import com.olxListing.olxproject.entity.CustomerMessages;

@Service
public interface MessagesService {

	String addMessages(CustomerMessages cm);

	String addAdminMessages(AdminMessages am);

	ResponseEntity<?> getCustomerMessages(String email);

	ResponseEntity<?> getAdminMessages(String email);
	
}
