package com.olxListing.olxproject.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.olxListing.olxproject.entity.Admin;
import com.olxListing.olxproject.entity.Listing;
import com.olxListing.olxproject.entity.Login;
import com.olxListing.olxproject.entity.User_Entity;
import com.olxListing.olxproject.repository.Admin_Repo;
import com.olxListing.olxproject.repository.Listing_Repo;
import com.olxListing.olxproject.repository.User_Repo;
import com.olxListing.olxproject.services.AdminService;

@Component
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	User_Repo userRepo;
	
	@Autowired
	Admin_Repo adminRepo;
	
	@Autowired
	Listing_Repo listingRepo;

	@Override
	public ResponseEntity<String> updateCustomer(String email, User_Entity user) {
		try {
			Admin admin = adminRepo.findAll().get(0);
			
			if(admin.isLoggedIn()) {
				User_Entity curr_user = userRepo.findBymail(email);
				
				if(curr_user == null) {
					return new ResponseEntity<String> ("Please enter a valid mail",HttpStatus.BAD_REQUEST);
				}
				
				if(user.getName() != null)
					curr_user.setName(user.getName());
				
				if(user.getLast_name() != null)
					curr_user.setLast_name(user.getLast_name());
				
				if(user.getContact_No() != 0)
					curr_user.setContact_No(user.getContact_No());
				
				if(user.getMail() != null)
					curr_user.setMail(user.getMail());
				
				if(user.getPassword() != null)
					curr_user.setPassword(user.getPassword());
				
				
				userRepo.save(curr_user);
				
				return new ResponseEntity<String> ("User data updated Successfully!",HttpStatus.OK);
			}
			
			return new ResponseEntity<String> ("You are not an admin",HttpStatus.BAD_REQUEST);
			
		}
		
		catch(Exception e) {
			return new ResponseEntity<String>("Please enter an email",HttpStatus.BAD_REQUEST);
		}
		}
		

	@Override
	public ResponseEntity<?> seeCustomers() {
		Admin admin = adminRepo.findAll().get(0);
		try {
			if(admin.isLoggedIn()) 
				return new ResponseEntity<List<User_Entity>>(userRepo.findAll(), HttpStatus.OK);
			return new ResponseEntity<String>("Admin not logged in ",HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("No cutomers available",HttpStatus.BAD_REQUEST);
		}
		
			
	}

	@Override
	public ResponseEntity<String> deactivateUser(String email) {
		Admin admin = adminRepo.findAll().get(0);
		try {
			if(admin.isLoggedIn()) {
				User_Entity user = userRepo.findBymail(email);
				user.setActivate(false);
				userRepo.save(user);
				
				return new ResponseEntity<String>("Customer is deactivated successfully!",HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("You are not an admin!",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("email is invalid",HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public String registerAdmin(Admin admin) {
		if(admin.getEmail()==null ){
			return "Please enter mail";
		}
		else if(admin.getPassword()==null) {
			return "Please enter your password";
		}
		adminRepo.save(admin);
		return "Admin is registered Successfully!";
	}

	@Override
	public String loginAdmin(Login login) {
		if(adminRepo.findByemail(login.getEmail()) != null) {
			Admin admin = adminRepo.findByemail(login.getEmail());
			
			if(login.getPassword().equals(admin.getPassword())) {
				admin.setLoggedIn(true);
				adminRepo.save(admin);
				return "Admin Logged In Successfully!";
			}
			else {
				return "Invalid Password";
			}
		}
		
		else {
			return "Invalid Credentials";
		}
	}

	@Override
	public ResponseEntity<?> getAllAdmin() {
		if(!adminRepo.findAll().isEmpty()) {
			return new ResponseEntity<List<Admin>>(adminRepo.findAll(),HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("No Admins available..",HttpStatus.BAD_REQUEST);
		
		
	}

	@Override
	public ResponseEntity<String> activateUser(String mail) {
		Admin admin = adminRepo.findAll().get(0);
		try {
			if(admin.isLoggedIn()) {
				User_Entity user = userRepo.findBymail(mail);
				user.setActivate(true);
				userRepo.save(user);
				
				return new ResponseEntity<String>("Customer is activated successfully!",HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("You are not an admin!",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("email is invalid",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public String logoutAdmin() {
		Admin admin = adminRepo.findAll().get(0);
		admin.setLoggedIn(false);
		adminRepo.save(admin);
		return "Logged out successfully!";
	}

	@Override
	public String removeListing(int id) {
		Admin admin = adminRepo.findAll().get(0);
		
		
		if(admin.isLoggedIn()) {
			try {
			listingRepo.deleteById(id);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "Listing is removed successfully!";
		}
		
		return "You are not an admin";
	}

	@Override
	public ResponseEntity<?> getActiveUsers() {
         Admin admin = adminRepo.findAll().get(0);
		try {
			if(admin.isLoggedIn()) {
				List<User_Entity> activeUsers = new ArrayList<>();
				List<User_Entity> allUsers = userRepo.findAll();
				
				for(User_Entity user : allUsers) {
					if(user.isActivate())
						activeUsers.add(user);
				}
				if(!activeUsers.isEmpty())
					return new ResponseEntity<List<User_Entity>> (activeUsers,HttpStatus.OK);
				else 
					return new ResponseEntity<String>("No active users",HttpStatus.BAD_REQUEST); 
			}
			return new ResponseEntity<String>("Admin is not logged in",HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		
		
		
	}

	@Override
	public ResponseEntity<?> getListingOfUser(String email) {
		try {
			 Admin admin = adminRepo.findAll().get(0);
			 if(admin.isLoggedIn()) {
				 if(!userRepo.findBymail(email).getListings().isEmpty())
					 return new ResponseEntity<List<Listing>> (userRepo.findBymail(email).getListings(),HttpStatus.OK);
				 return new ResponseEntity<String>("No listings for the user...",HttpStatus.OK);
			 }
			 return new ResponseEntity<String>("Admin is not logged in...",HttpStatus.BAD_REQUEST);
			
		}catch(Exception e) {
			return new ResponseEntity<String>("Invalid email",HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> getExpiredListing() {
		try {
			Admin admin = adminRepo.findAll().get(0);
			 if(admin.isLoggedIn()) {
					 List<Listing> expiredListing = new ArrayList<>();
						List<Listing> allListings = listingRepo.findAll();
						Calendar calendar = Calendar.getInstance();
						Date curr_date = calendar.getTime();
						
						for(Listing listing : allListings) {
							if(listing.getExpiryDate().before(curr_date)) {
								expiredListing.add(listing);
							}
						}
					if(!expiredListing.isEmpty()) {
						return new ResponseEntity<List<Listing>>(expiredListing,HttpStatus.OK);
					}
					return new ResponseEntity<String>("No list available...",HttpStatus.BAD_REQUEST);
			 }
			return new ResponseEntity<String>("Admin is not logged in...",HttpStatus.BAD_REQUEST);
			
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
