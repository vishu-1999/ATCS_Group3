package com.olxListing.olxproject.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.olxListing.olxproject.entity.Listing;

@Repository
public interface Listing_Repo extends JpaRepository<Listing, Integer>{
	public List<Listing> findBycategory(String category);
	
//	@Query("SELECT location FROM Listing listing")
//	public List<HashMap<String, String>> findBylocation();
	
	@Query(value = "SELECT * FROM listing l WHERE l.price <= ?1", 
			nativeQuery = true)
	public List<Listing> findItemsByPrice(int price);

	
	

}
