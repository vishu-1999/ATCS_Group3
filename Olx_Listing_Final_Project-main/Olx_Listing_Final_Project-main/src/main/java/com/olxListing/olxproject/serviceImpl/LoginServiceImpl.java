package com.olxListing.olxproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxListing.olxproject.entity.Login;
import com.olxListing.olxproject.entity.User_Entity;
import com.olxListing.olxproject.repository.User_Repo;
import com.olxListing.olxproject.services.LoginService;

@Component
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	User_Repo userRepo;

	@Override
	public String loginUser(Login login) {
		if(userRepo.findBymail(login.getEmail()) != null) {
			User_Entity user = userRepo.findBymail(login.getEmail());
			
			if(login.getPassword().equals(user.getPassword())) {
				user.setLoggedIn(true);
				userRepo.save(user);
				return "Logged In Successfully!";
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
	public String logoutUser(String email) {
		try {
			User_Entity user = userRepo.findBymail(email);
			user.setLoggedIn(false);
			userRepo.save(user);
			return "Logged out successfully!";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "enter mail";
		}
		
	}

}
