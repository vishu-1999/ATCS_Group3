package com.olxListing.olxproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olxListing.olxproject.entity.Login;
import com.olxListing.olxproject.services.LoginService;

@RestController
public class LoginController {
	
	@Autowired 
	LoginService loginService;
	
	@GetMapping("/login")
	public String loginUser(@RequestBody Login login) {
		return loginService.loginUser(login);
	}
	
	@GetMapping("/logout/{email}")
	public String logoutUser(@PathVariable("email") String email) {
		return loginService.logoutUser(email);
	}

}
