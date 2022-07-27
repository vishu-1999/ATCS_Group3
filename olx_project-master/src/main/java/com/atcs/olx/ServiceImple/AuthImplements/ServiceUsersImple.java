package com.atcs.olx.ServiceImple.AuthImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atcs.olx.Entity.Authenticate.ActiveUsers;
import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Repository.AuthenticateRepo.AdminRegisterRepo;
import com.atcs.olx.Repository.AuthenticateRepo.RegisterRepo;
import com.atcs.olx.Repository.ProductRepo.ProductRepo;
import com.atcs.olx.Service.ServiceAuthenticate.ServiceAuthenticate;

@Component
public class ServiceUsersImple implements ServiceAuthenticate {

    @Autowired
    RegisterRepo registerRepo;

    @Autowired
    AdminRegisterRepo adminRegisterRepo;

    @Autowired
    ProductRepo productRepo;

    @Override
    public String registerUsers(Register register) {
        registerRepo.save(register);
        return "Registration Successfull!";
    }

    public boolean isValidPassword(String password) {
        String regex_pass = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex_pass);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    @Override
    public List<Register> getAllUsers() {
        return registerRepo.findAll();
    }

    @Override
    public Register getUserById(long id) {
        return registerRepo.findById(id).get();
    }

    @Override
    public Register updateUserPasswordById(Register register) {
        return registerRepo.save(register);
    }

    @Override
    public boolean setUserLoggedIn(Register register) {
        register.setUserLoggedIn(true);
        registerRepo.save(register);
        return true;
    }

    @Override
    public boolean setUserLoggedOut(Register register) {
        register.setUserLoggedIn(false);
        registerRepo.save(register);
        return false;
    }

    @Override
    public String registerAdmin(Admin_Register admin_register) {
        adminRegisterRepo.save(admin_register);
        return "Admin Registration Successfull!";
    }

    @Override
    public boolean setAdminLoggedIn(Admin_Register admin_register) {
        admin_register.setUserLoggedIn(true);
        adminRegisterRepo.save(admin_register);
        return true;
    }

    @Override
    public boolean setAdminLoggedOut(Admin_Register admin_register) {
        admin_register.setUserLoggedIn(false);
        adminRegisterRepo.save(admin_register);
        return false;
    }

    @Override
    public List<Admin_Register> getAllAdmin() {
        return adminRegisterRepo.findAll();
    }

    @Override
    public Admin_Register getAdminById(long id) {
        return adminRegisterRepo.findById(id).get();
    }

    @Override
    public Admin_Register updateAdminPasswordById(Admin_Register admin_register) {
        return adminRegisterRepo.save(admin_register);
    }

    @Override
    public List<ActiveUsers> activeUsers() {
        List<Register> allUsers = registerRepo.findAll();
        List<ActiveUsers> activeList = new ArrayList<ActiveUsers>();

        for (Register a : allUsers) {
            if (a.isUserLoggedIn() == true) {
                String name = a.getFirstname() + " " + a.getLastname();
                String email = a.getEmail();
                ActiveUsers act = new ActiveUsers(name, email);
                activeList.add(act);
            }
        }
        return activeList;
    }

    @Override
    public boolean checkUsers(String email){
        List<Register> allUsers = registerRepo.findAll();
        for(Register a: allUsers){
            if(email.equals(a.getEmail())){
                if(a.isUserLoggedIn() == true){
                    return true;
                }
            }
        }
        return false;
       
    }

    @Override
    public boolean checkAdmin(String email){
        List<Admin_Register> allAdmin = adminRegisterRepo.findAll();
        for(Admin_Register a: allAdmin){
            System.out.println(a.isUserLoggedIn());
            if(email.equals(a.getEmail())){
                if(a.isUserLoggedIn() == true){
                   
                    return true;
                }
                else{
                    return false; 
                }
            }
        }
        return false; 
    }

    @Override
    public Register updateUser(Long id, Register register){
        Register oldDetails = registerRepo.findById(id).get();
        if(register.getFirstname() == null ){
            register.setFirstname(oldDetails.getFirstname());
        }
        if(register.getLastname() == null){
            register.setLastname(oldDetails.getLastname());
        }
        if(register.getEmail() == null){
            register.setEmail(oldDetails.getEmail());
        }
        if(register.getPhone_number() == null){
            register.setPhone_number(oldDetails.getPhone_number());
        }
        if(register.getId() == null){
            register.setId(oldDetails.getId());
        }
        if(register.getPassword() == null){
            register.setPassword(oldDetails.getPassword());
        }
        if(register.getProduct() == null){
            register.setProduct( oldDetails.getProduct());
        }
        if(register.isUserLoggedIn() == false || register.isUserLoggedIn() == true){
            register.setUserLoggedIn(oldDetails.isUserLoggedIn());
        }
        if(register.getCreated_date() != null || register.getCreated_date() == null){
            register.setCreated_date(oldDetails.getCreated_date());
        }
        return registerRepo.save(register); 
    }

    @Override
    public void removeUserbyId(Long id){
        registerRepo.deleteById(id);
    }

    @Override
   public String removeProductByUser(Long id){
       Register user = registerRepo.findById(id).get();
       List<Product> prod =  user.getProduct();
       if(prod.isEmpty()){
            return "No product listed with this user!";
       }else{
            for(Product p: prod){
                productRepo.deleteById(p.getId());
       }
       return "Product removed Successfully!";
       }
      
    }
}
