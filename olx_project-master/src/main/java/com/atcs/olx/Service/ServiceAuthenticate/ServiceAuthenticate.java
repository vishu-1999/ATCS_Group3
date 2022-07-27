package com.atcs.olx.Service.ServiceAuthenticate;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atcs.olx.Entity.Authenticate.ActiveUsers;
import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Register;

@Service
public interface ServiceAuthenticate {
    public String registerUsers(Register register);
    public List<Register> getAllUsers();
    public Register getUserById(long id);
    public Register updateUserPasswordById(Register register);
    public boolean  isValidPassword(String password);
    public boolean setUserLoggedIn(Register register);
    public boolean setUserLoggedOut(Register register);
    public String registerAdmin(Admin_Register admin_register);
    public boolean setAdminLoggedIn(Admin_Register admin_register);
    public boolean setAdminLoggedOut(Admin_Register admin_register);
    public List<Admin_Register> getAllAdmin();
    public Admin_Register getAdminById(long id);
    public Admin_Register updateAdminPasswordById(Admin_Register getAdmin);
    public List<ActiveUsers> activeUsers();
    public boolean checkUsers(String email);
    public boolean checkAdmin(String email);
    public Register updateUser(Long id, Register register);
    public void removeUserbyId(Long id);
    public String removeProductByUser(Long id);
   
}
