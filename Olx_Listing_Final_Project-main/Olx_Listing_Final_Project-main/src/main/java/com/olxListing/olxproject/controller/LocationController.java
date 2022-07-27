package com.olxListing.olxproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olxListing.olxproject.entity.Location;
import com.olxListing.olxproject.repository.Location_Repo;

@RestController
public class LocationController {
	
	@Autowired Location_Repo locationRepo;
	
	
	@GetMapping("/seelocation")
	public List<Location> getLocation() {
		return locationRepo.findAll();
	}
	
//	@PostMapping("/postLocations")
//	public void addLocation(@RequestBody Location location) {
//		locationRepo.save(location);
//	}
}
