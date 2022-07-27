package com.atcs.olx.Service.MessagesService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Messages.AllAdminMessages;
import com.atcs.olx.Entity.Messages.AllMessages;

@Service
public interface MessageService {
    public void storeMessages(Register register, Long receiverId, String message);
    public void storeAdminMessages(Admin_Register current_admin,Register userReceiver, Admin_Register adminReceiver, String message);
    public List<AllMessages> getAllmessagesByUser(Register user);
    public List<AllAdminMessages> getAllmessagesByAdmin(Admin_Register admin);
}
