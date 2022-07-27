package com.olxListing.olxproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxListing.olxproject.entity.Admin;

@Repository
public interface Admin_Repo extends JpaRepository<Admin, Integer>{
	public Admin findByemail(String email);

}
