package com.atcs.olx.Repository.AuthenticateRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.atcs.olx.Entity.Authenticate.Register;


@Repository
public interface RegisterRepo extends JpaRepository<Register,Long>{

    
}
