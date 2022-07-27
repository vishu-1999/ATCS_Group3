package com.atcs.olx.Repository.AuthenticateRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atcs.olx.Entity.Messages.AllMessages;

@Repository
public interface ALlMessagesRepo extends JpaRepository<AllMessages,Long>{
    
}
