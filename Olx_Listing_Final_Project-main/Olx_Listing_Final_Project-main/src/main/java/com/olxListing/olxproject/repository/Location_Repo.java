package com.olxListing.olxproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxListing.olxproject.entity.Location;

@Repository
public interface Location_Repo extends JpaRepository<Location, Integer>{

}
