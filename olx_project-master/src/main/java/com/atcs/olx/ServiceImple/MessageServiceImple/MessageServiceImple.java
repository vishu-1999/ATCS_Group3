package com.atcs.olx.ServiceImple.MessageServiceImple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Messages.AllAdminMessages;
import com.atcs.olx.Entity.Messages.AllMessages;
import com.atcs.olx.Repository.AuthenticateRepo.ALlMessagesRepo;
import com.atcs.olx.Repository.AuthenticateRepo.AllAdminMessagesRepo;
import com.atcs.olx.Repository.AuthenticateRepo.RegisterRepo;
import com.atcs.olx.Service.MessagesService.MessageService;


@Component
public class MessageServiceImple implements MessageService{
    @Autowired
    RegisterRepo registerRepo;

    @Autowired
    ALlMessagesRepo allMessagesRepo;

    @Autowired
    AllAdminMessagesRepo allAdminMessagesRepo;

  
    @Override
     public void storeMessages(Register register, Long receiverId,String message) {
        AllMessages am = new AllMessages( receiverId, message, LocalDateTime.now() ,register);  
        allMessagesRepo.save(am);

    }

    @Override
    public void storeAdminMessages(Admin_Register current_admin, Register userReceiver, Admin_Register adminReceiver,
        String message) {
            String userReceiverId = "NA";
            String adminReceiverId ="NA";
            if(userReceiver != null){
                userReceiverId = userReceiver.getId().toString();
                
            } 
            if(adminReceiver != null){
                adminReceiverId = adminReceiver.getId().toString();
            }
                 
        AllAdminMessages adm = new AllAdminMessages(userReceiverId,adminReceiverId,message,LocalDateTime.now(),current_admin);
       allAdminMessagesRepo.save(adm);
    }


    @Override
    public List<AllMessages> getAllmessagesByUser(Register user) {
      List<AllMessages> allmsgs =  allMessagesRepo.findAll();
      List<AllMessages> allMsgsByUser = new ArrayList<AllMessages>();
      for(AllMessages m: allmsgs){
        if(m.getRegister().getId().equals(user.getId())){
            allMsgsByUser.add(m);
        }
      }
      return allMsgsByUser;
    }


    @Override
    public List<AllAdminMessages> getAllmessagesByAdmin(Admin_Register admin){
        List<AllAdminMessages> allmsgs =  allAdminMessagesRepo.findAll();
        List<AllAdminMessages> allMsgsByAdmin = new ArrayList<AllAdminMessages>();
        for(AllAdminMessages m: allmsgs){
          if(m.getAdminRegister().getId().equals(admin.getId())){
              allMsgsByAdmin.add(m);
          }
        }
        return allMsgsByAdmin;
    }
    
}
