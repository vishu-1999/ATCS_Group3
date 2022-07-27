package com.olxListing.olxproject.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.olxListing.olxproject.entity.Admin;
import com.olxListing.olxproject.entity.AdminMessages;
import com.olxListing.olxproject.entity.CustomerMessages;
import com.olxListing.olxproject.entity.User_Entity;
import com.olxListing.olxproject.repository.AdminMessagesRepo;
import com.olxListing.olxproject.repository.Admin_Repo;
import com.olxListing.olxproject.repository.CustomerMessagesRepo;
import com.olxListing.olxproject.repository.User_Repo;
import com.olxListing.olxproject.services.MessagesService;

@Component
public class MessagesServiceImpl implements MessagesService{
	
	@Autowired
	CustomerMessagesRepo customerMessagesRepo;
	
	@Autowired
	AdminMessagesRepo adminMessagesRepo;
	
	@Autowired
	User_Repo userRepo;
	
	@Autowired 
	Admin_Repo adminRepo;

	@Override
	public String addMessages(CustomerMessages cm) {
		User_Entity user1 = userRepo.findBymail(cm.getsenderEmail());
		User_Entity user2 = userRepo.findBymail(cm.getreceiverMail());
		if(user1==null && user2==null) {
			return "Enter mails correctly";
		}
		else if(user2==null) {
			return "Enter valid receiver's mail";
		}
		else if(user1==null)
			return "Enter valid sender's mail";
		else {
			cm.setsender(user1);
			cm.setreceiver(user2);
			
			customerMessagesRepo.save(cm);
			return "Message is sent successfully!";
		}
		
	}

	@Override
	public String addAdminMessages(AdminMessages am) {
		Admin admin = adminRepo.findByemail(am.getadminMail());
		User_Entity user = userRepo.findBymail(am.getcustMail());
		if(admin==null && user==null) {
			return "Enter mails correctly";
		}
		else if(user==null) {
			return "Enter valid receiver's mail";
		}
		else if(admin==null)
			return "Enter valid admin's mail";
		else {
		am.setAdmin(admin);
		am.setUser(user);
		
		adminMessagesRepo.save(am);
		return "Message is sent successfully!";
		}
	}

	@Override
	public ResponseEntity<?> getCustomerMessages(String email) {
		try {
			List<CustomerMessages> allMessages = customerMessagesRepo.findAll();
			List<CustomerMessages> customerMessages = new ArrayList<>();
			
			for(CustomerMessages cm : allMessages) {
				if(cm.getsenderEmail().equals(email) || cm.getreceiverMail().equals(email)) {
					customerMessages.add(cm);
				}
			}
			if(!customerMessages.isEmpty())
				return new ResponseEntity<List<CustomerMessages>>( customerMessages,HttpStatus.OK);
			else 
				return new ResponseEntity<String>("Enter valid email",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> getAdminMessages(String email) {
		try {
			List<AdminMessages> allMessages = adminMessagesRepo.findAll();
			List<AdminMessages> adminMessages = new ArrayList<>();
			
			for(AdminMessages am : allMessages) {
				if(am.getadminMail().equals(email) || am.getcustMail().equals(email)) {
					adminMessages.add(am);
				}
			}
			if(!adminMessages.isEmpty())
				return new ResponseEntity<List<AdminMessages>>( adminMessages,HttpStatus.OK);
			else 
				return new ResponseEntity<String>("Enter valid email",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
 