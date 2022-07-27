package com.olxListing.olxproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxListing.olxproject.entity.User_Entity;

@Repository
public interface User_Repo extends JpaRepository<User_Entity,Integer>{
	
	public User_Entity findBymail(String mail);



}
