package com.atcs.olx.Repository.AuthenticateRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atcs.olx.Entity.Authenticate.Admin_Register;

@Repository
public interface AdminRegisterRepo extends JpaRepository<Admin_Register,Long>{
    
}
