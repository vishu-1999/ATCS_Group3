package com.atcs.olx.Controller.AuthenticateController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.atcs.olx.Entity.Authenticate.ActiveUsers;
import com.atcs.olx.Entity.Authenticate.Admin_Register;
import com.atcs.olx.Entity.Authenticate.Forgot;
import com.atcs.olx.Entity.Authenticate.LogOut;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Authenticate.SignIn;
import com.atcs.olx.Entity.Products.ListProductByEmail;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Service.ServiceAuthenticate.ServiceAuthenticate;
import com.atcs.olx.Service.UsersProductService.UserProductService;
import com.google.common.hash.Hashing;

@RestController
@RequestMapping("/olx")
public class Authenticate {

    @Autowired
    ServiceAuthenticate serviceUsers;

    @Autowired
    UserProductService userProductService;
   
    String email_regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; 
    Pattern email = Pattern.compile(email_regex); 
    String msg = "";
    public static boolean checkUser = false;
    public static boolean checkAdmin = false;
    

    // Register New User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Register register){
        
        Matcher email_matcher = email.matcher(register.getEmail());

        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(register.getPassword()) == false){
            msg = "Password is not valid! (e.g: 8 characters length, 1 letters in Upper Case, 1 Special Character (!@#$&*), 2 numerals (0-9), 3 letters in Lower Case )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(register.getPhone_number().length() < 10 || register.getPhone_number().length() > 10){
            msg = "Incorrect phone number!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else{
            List<Register> allUsers = serviceUsers.getAllUsers();
            for(Register r: allUsers){
                if(r.getEmail().equals(register.getEmail())){
                    msg = "Email already exist!";
                    return  new ResponseEntity<String>(msg,HttpStatus.OK);
                }
            }
            register.setPassword(Hashing.sha256()
            .hashString(register.getPassword(), StandardCharsets.UTF_8)
            .toString());

            serviceUsers.registerUsers(register);
            msg = "Registration Successfull!";
            return  new ResponseEntity<String>(msg,HttpStatus.OK);
        }   
    }

    // User Forgot their Password
    @PutMapping("/forgot_password")
    public ResponseEntity<String> forgot_password(@RequestBody Forgot forgot){
        Matcher email_matcher = email.matcher(forgot.getEmail());
        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(forgot.getPassword()) == false){
            msg = "Password is not valid! (e.g: 8 characters length, 2 letters in Upper Case, 1 Special Character (!@#$&*), 2 numerals (0-9), 3 letters in Lower Case )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Register> allUsers = serviceUsers.getAllUsers();
            for(Register r: allUsers){
                if(r.getEmail().equals(forgot.getEmail())){
                   Long id = r.getId();
                   Register getUser = serviceUsers.getUserById(id);
                   getUser.setPassword(Hashing.sha256()
                   .hashString(forgot.getPassword(), StandardCharsets.UTF_8)
                   .toString());
                   serviceUsers.updateUserPasswordById(getUser);
                   msg = "Password reset Successfull!";
                   return new ResponseEntity<String>(msg,HttpStatus.OK);
                }
            }
            msg = "No account with this email id!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);           
        } 
    }

    //  user login/SignIn
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn_User(@RequestBody SignIn signIn){
        Matcher email_matcher = email.matcher(signIn.getEmail());
        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(signIn.getPassword()) == false){
            msg = "Password is not valid! (e.g: 8 characters length, 1 letters in Upper Case, 1 Special Character (!@#$&*), 1 numerals (0-9) )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Register> allUsers = serviceUsers.getAllUsers();
            for(Register r: allUsers){
                if(r.getEmail().equals(signIn.getEmail())){
                   Long id = r.getId();  
                   Register getUser = serviceUsers.getUserById(id);
                   if(getUser.getPassword().equals(Hashing.sha256()
                   .hashString(signIn.getPassword(), StandardCharsets.UTF_8)
                   .toString())){
                    serviceUsers.setUserLoggedIn(getUser);
                    checkUser =  serviceUsers.checkUsers(getUser.getEmail());
                    msg = "Login Successfull!!";
                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                   }else{
                    msg = "Incorrect Password!";
                    return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY); 
                   }        
                  
                }
            }
                    
        }
        msg = "No account with this email id!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);  
    }

    // LogOut User
    @PostMapping("/logOut")
    public ResponseEntity<String> logout_User(@RequestBody LogOut logOut){
        Matcher email_matcher = email.matcher(logOut.getEmail());
        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Register> allUsers = serviceUsers.getAllUsers();
            for(Register r: allUsers){
                if(r.getEmail().equals(logOut.getEmail())){
                   Long id = r.getId();  
                   Register getUser = serviceUsers.getUserById(id);
                    serviceUsers.setUserLoggedOut(getUser);
                    msg = "LogOut Successfull!!";
                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                   }                         
                }
            }
        msg = "No account with this email id!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);  
    }


    // Register New Admin
    @PostMapping("/admin/register")
    public ResponseEntity<String> admin_register(@RequestBody Admin_Register admin_register){
        
        Matcher email_matcher = email.matcher(admin_register.getEmail());

        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(admin_register.getPassword()) == false){
            msg = "Password is not valid! (e.g: 8 characters length, 2 letters in Upper Case, 1 Special Character (!@#$&*), 2 numerals (0-9), 3 letters in Lower Case )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(admin_register.getPhone_number().length() < 10 || admin_register.getPhone_number().length() > 10){
            msg = "Incorrect phone number!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else{
            List<Admin_Register> allAdmin = serviceUsers.getAllAdmin();
            for(Admin_Register r: allAdmin){
                if(r.getEmail().equals(admin_register.getEmail())){
                    msg = "Email already exist!";
                    return  new ResponseEntity<String>(msg,HttpStatus.OK);
                }
            }
            admin_register.setPassword(Hashing.sha256()
            .hashString(admin_register.getPassword(), StandardCharsets.UTF_8)
            .toString());

            serviceUsers.registerAdmin(admin_register);
            msg = "Admin Registration Successfull!";
            return  new ResponseEntity<String>(msg,HttpStatus.OK);
        }   
    }

    // Admin login/SignIn
    @PostMapping("/admin/signIn")
    public ResponseEntity<String> signIn_Admin(@RequestBody SignIn signIn){
        Matcher email_matcher = email.matcher(signIn.getEmail());
        if(email_matcher.matches() == false){
            msg = "Admin email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(signIn.getPassword()) == false){
            msg = "Admin password is not valid! (e.g: 8 characters length, 2 letters in Upper Case, 1 Special Character (!@#$&*), 2 numerals (0-9), 3 letters in Lower Case )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Admin_Register> allUsers = serviceUsers.getAllAdmin();
            for(Admin_Register r: allUsers){
                if(r.getEmail().equals(signIn.getEmail())){
                   Long id = r.getId();  
                   Admin_Register getAdmin = serviceUsers.getAdminById(id);
                   if(getAdmin.getPassword().equals(Hashing.sha256()
                   .hashString(signIn.getPassword(), StandardCharsets.UTF_8)
                   .toString())){
                    serviceUsers.setAdminLoggedIn(getAdmin);
                    msg = "Admin Login Successfull!!";
                    checkAdmin =  serviceUsers.checkAdmin(getAdmin.getEmail());
                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                   }else{
                    msg = "Incorrect admin Password!";
                    return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY); 
                   }        
                  
                }
            }
                    
        }
        msg = "No admin account with this email id!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);  
    }

    // Admin Forgot Password
    @PutMapping("/admin/forgot_password")
    public ResponseEntity<String> forgot_Admin_Password(@RequestBody Forgot forgot){
        Matcher email_matcher = email.matcher(forgot.getEmail());
        if(email_matcher.matches() == false){
            msg = "Admin email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else if(serviceUsers.isValidPassword(forgot.getPassword()) == false){
            msg = "Admin password is not valid! (e.g: 8 characters length, 1 letters in Upper Case, 1 Special Character (!@#$&*), 1 numerals (0-9) )";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Admin_Register> allUsers = serviceUsers.getAllAdmin();
            for(Admin_Register r: allUsers){
                if(r.getEmail().equals(forgot.getEmail())){
                   Long id = r.getId();
                   Admin_Register getAdmin = serviceUsers.getAdminById(id);
                   getAdmin.setPassword(Hashing.sha256()
                   .hashString(forgot.getPassword(), StandardCharsets.UTF_8)
                   .toString());
                   serviceUsers.updateAdminPasswordById(getAdmin);
                   msg = "Admin password reset Successfull!";
                   return new ResponseEntity<String>(msg,HttpStatus.OK);
                }
            }
            msg = "No admin account with this email id!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);           
        }
     
    }

    // LogOut Admin
    @PostMapping("/admin/logOut")
    public ResponseEntity<String> logout_Admin(@RequestBody LogOut logOut){
        Matcher email_matcher = email.matcher(logOut.getEmail());
        if(email_matcher.matches() == false){
            msg = "Email is not valid! (e.g: email@email.com)";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        else {
            List<Admin_Register> allUsers = serviceUsers.getAllAdmin();
            for(Admin_Register r: allUsers){
                if(r.getEmail().equals(logOut.getEmail())){
                   Long id = r.getId();  
                   Admin_Register getAdmin = serviceUsers.getAdminById(id);
                    serviceUsers.setAdminLoggedOut(getAdmin);
                    msg = "Admin LogOut Successfull!!";
                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                   }                         
                }
            }
        msg = "No account with this email id!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);  
    }

    // Get all Active users list
    @GetMapping("/admin/active_users")
    public ResponseEntity<?> getActiveUsers(){
        if (checkAdmin == true){
           
            if(serviceUsers.activeUsers() != null){
                return new ResponseEntity<List<ActiveUsers>>(serviceUsers.activeUsers(),HttpStatus.OK);
            }
            else{
                msg = "No Active Users Right Now!";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            }
        }else{
            msg = "You are not an Admin. LogIn First!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
      
    }


    // Admin can get all list of users
    @GetMapping("/admin/get_all_users")
    public ResponseEntity<?> getAllUsers(){
        if (checkAdmin == true){
           
            if(serviceUsers.getAllUsers() != null){
                return new ResponseEntity<List<Register>>(serviceUsers.getAllUsers(),HttpStatus.OK);
            }
            else{
                msg = "No Registered User Right Now!";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            }
        }else{
            msg = "You are not an Admin. LogIn First!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
        
    }

    // Admin can update user details
    @PutMapping("/admin/update_user_details/{id}")
    public ResponseEntity<String> updateUserdetails(@PathVariable("id") long id,@RequestBody Register register){
        List<Register> allUsers = serviceUsers.getAllUsers();
        Long ids = id;
        if(checkAdmin == true){
            for(Register a: allUsers){
                if(a.getId().equals(ids)){
                    serviceUsers.updateUser(id,register);
                    msg = "Update details successfull!";
                    return  new ResponseEntity<String>(msg,HttpStatus.OK);
                }
            }
            msg = "User not Found with this user Id!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);

        }
        msg = "You are not an admin! Please logIn!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_REQUEST);
    }
    

    // Admin can delete the user
    @DeleteMapping("/admin/remove_user/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable("id") long id){
        if(checkAdmin == true){
            Register user = serviceUsers.getUserById(id);
            if(user != null){
                serviceUsers.removeUserbyId(id);
                msg = "User removed Successfully!";
                return new ResponseEntity<String>(msg,HttpStatus.OK);
            }
            else{
                msg = "Invalid user Id!";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            }
        } else{
            msg = "You are not an admin! Please logIn!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }

    }

    // Admin can delete the listed product by the specific user
    @DeleteMapping("/admin/delete_product_by_user/{id}")
    public ResponseEntity<String> removeProductByUser(@PathVariable("id") Long id){
        if(checkAdmin == true){
            Register user = null;
            try{
                user = serviceUsers.getUserById(id);
                if(user != null){
               
                    msg = serviceUsers.removeProductByUser(id);
                    return new ResponseEntity<String>(msg,HttpStatus.OK);
                }
                else{
                    msg = "Invalid user Id!";
                    return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
                }
            }
            catch(Exception e){
                msg = "Invalid user Id!";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            }
           
        } else{
            msg = "You are not an admin!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
    }


    // Admin can list all the product of the specific user
    @GetMapping("admin/list_product_by_user")
    public ResponseEntity<?> adminListProductByUser(@RequestBody ListProductByEmail listProductByEmail){
        if(checkAdmin == true){
            List<Register> allUsers = serviceUsers.getAllUsers();
            for(Register a: allUsers){
                if(a.getEmail().equals(listProductByEmail.getEmail())){  
                    return new ResponseEntity<List<Product>>(userProductService.listProductByUser(a),HttpStatus.OK);
                } 
            }
            msg = " email not found!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }else{
            msg = "You are not an admin!";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        }
    }

}


