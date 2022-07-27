package com.olxListing.olxproject.services;

import org.springframework.stereotype.Service;

import com.olxListing.olxproject.entity.Login;

@Service
public interface LoginService {

	public String loginUser(Login login);

	public String logoutUser(String email);

}
