package com.atcs.olx.Controller.UsersMessageController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atcs.olx.Controller.AuthenticateController.Authenticate;
import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Messages.AllAdminMessages;
import com.atcs.olx.Entity.Messages.AllMessages;
import com.atcs.olx.Entity.Messages.Message;
import com.atcs.olx.Service.MessagesService.MessageService;
import com.atcs.olx.Service.ServiceAuthenticate.ServiceAuthenticate;


@RestController
@RequestMapping("/olx")
public class UsersMessage {
    @Autowired
    ServiceAuthenticate serviceUsers;

    @Autowired
    MessageService messageService;

    String msg = "";

    // User can send message to another user using their email
    @PostMapping("/user/send_message_to" )
    public ResponseEntity<String> sendMessage(@RequestBody Message message){
        try{ 
            if(Authenticate.checkUser == true){
                Register current_user = null;
                List<Register> usersList = serviceUsers.getAllUsers();
                for(Register r: usersList){
                    if(message.getEmail().equals(r.getEmail())){
                            for(Register cu : usersList){
                            if(cu.isUserLoggedIn() == true){
                                if(message.getEmail().equals(cu.getEmail())){
                                    msg = "You are not allowed to send message to yourSelf!";
                                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                                }
                                    current_user = cu;
                                    messageService.storeMessages(current_user,r.getId(),message.getMessage());
                                    msg = "Message Sent!";
                                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                                }
                            }                            
                        }                       
                    }
                    msg = "Receiver email is not valid!";  
                    return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
                } 
                msg = "Please login first!";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
              
        }catch(Exception e){
            msg = e.toString();
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
    }

    // Admin can send message to any one using email 
    @PostMapping("/admin/send_message_to" )
    public ResponseEntity<String> sendAdminMessages(@RequestBody Message message){
        try{ 
            if(Authenticate.checkAdmin == true){
                List<Register> usersList = serviceUsers.getAllUsers();
                List<Admin_Register> adminList = serviceUsers.getAllAdmin();
                Admin_Register adDeails = null;
                Register rDetails = null;
                for(Register r: usersList){
                    for(Admin_Register ar: adminList){
                        if(message.getEmail().equals(r.getEmail()) || message.getEmail().equals(ar.getEmail()) ){
                            if(message.getEmail().equals(r.getEmail())){
                                rDetails = r;
                            }
                            if(message.getEmail().equals(ar.getEmail())){
                                adDeails = ar;
                            }
                            if(ar.isUserLoggedIn() == true){
                                if(message.getEmail().equals(ar.getEmail())){
                                    msg = "You are not allowed to send message to yourSelf!";
                                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                                }
                                messageService.storeAdminMessages(ar, rDetails, adDeails , message.getMessage());
                                // 1: Current loggedIn admin, 2. If User then U_Details, 3. If Admin then A_Details, 4. Messages.
                                msg = "Message Sent!";
                                System.out.println(msg);
                                return new ResponseEntity<String>(msg,HttpStatus.OK);
                            
                            }
                        }
                        
                    }
                }
                msg = "Receiver email is not valid!!";
                return new ResponseEntity<String>(msg,HttpStatus.OK);
            }
            msg = "Please login first!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }catch(Exception e){
            msg =  e.toString();
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // User can get all the previous messages 
    @GetMapping("/user/get_all_messages")
    public ResponseEntity<?> getAllUserMessages(){
        List<String> ms = new ArrayList<String>();
        try{
            if(Authenticate.checkUser == true){
                List<Register> allUsers = serviceUsers.getAllUsers();
                List<AllMessages> allMsgs  = null;
                for(Register user: allUsers){
                    if(user.isUserLoggedIn() == true){
                        allMsgs = messageService.getAllmessagesByUser(user);
                    }
                }
                if(allMsgs == null){
                    msg = "No message found!";
                    return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
                }else{
                    List<Register> allRegister = serviceUsers.getAllUsers();
                    String msgByUser = null;
                    for(AllMessages a: allMsgs){
                        for(Register r: allRegister){
                            if(r.getId().equals(a.getReceiverId())){
                                msgByUser = "message: " + a.getMessage() +", Send to: "+ r.getFirstname() + " "+ r.getLastname() + ", date: " + a.getDate();
                                ms.add(msgByUser);
                            }      
                        }  
                    }    
                }
                return new ResponseEntity<List<String>>(ms,HttpStatus.OK);
            }   
            msg = "Please login first!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            
        }catch(Exception e){
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // Admin can list all the previous messages
    @GetMapping("/admin/get_all_messages")
    public ResponseEntity<?> getAllAdminMessages(){
        List<String> ms = new ArrayList<String>();
        try{
            if(Authenticate.checkAdmin == true){
                List<Admin_Register> allAdmin = serviceUsers.getAllAdmin();
                List<AllAdminMessages> allMsgs  = null;
                for(Admin_Register admin: allAdmin){
                    if(admin.isUserLoggedIn() == true){
                        allMsgs = messageService.getAllmessagesByAdmin(admin);
                    }
                }
                if(allMsgs == null){
                    msg = "No message found!";
                    return new ResponseEntity<String>(msg ,HttpStatus.BAD_GATEWAY);
                }else{
                    String msgByUser = null;
                    for(AllAdminMessages a: allMsgs){
                        for(Admin_Register r: allAdmin){
                            if(r.getId().toString().equals(a.getReceiverUserId())){
                                msgByUser = "message: " + a.getMessage() +", Send to: "+ r.getFirstname() + " "+ r.getLastname() + ", date: " + a.getDate();
                                ms.add(msgByUser);
                            }
                            if(r.getId().toString().equals(a.getReceiverAdminId())){
                                msgByUser = "message: " + a.getMessage() +", Send to: "+ r.getFirstname() + " "+ r.getLastname() + ", date: " + a.getDate();
                                ms.add(msgByUser);
                            }

                        }  
                    }    
                }
                return new ResponseEntity<List<String>>(ms,HttpStatus.OK);
            }   
            msg = "Admin Please login first!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            
        }catch(Exception e){
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }
}
