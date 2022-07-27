package com.atcs.olx.ServiceImple.UserProductImple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Products.Contact;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Entity.Products.SoldProducts;
import com.atcs.olx.Repository.AuthenticateRepo.AdminRegisterRepo;
import com.atcs.olx.Repository.AuthenticateRepo.RegisterRepo;
import com.atcs.olx.Repository.ProductRepo.ProductRepo;
import com.atcs.olx.Repository.ProductRepo.SoldProductsRepo;
import com.atcs.olx.Service.UsersProductService.UserProductService;

@Component
public class UserProductImple implements UserProductService{

    @Autowired
    ProductRepo productRepo;

    @Autowired
    RegisterRepo registerRepo;

    @Autowired 
    SoldProductsRepo soldProductsRepo;

    @Autowired
    AdminRegisterRepo adminRegisterRepo;


    @Override
    public String addProduct(Product product) {   
        productRepo.save(product);
        return "Product added Successfully!";
    }

    @Override
    public List<Product> listProductByUser(Register user){    
        List<Product> newUserProduct = new ArrayList<Product>();     
        newUserProduct = user.getProduct();
        System.out.println(newUserProduct.toString());
        return newUserProduct;
    }

    @Override
    public Contact getContactDetails(Product prod){
        String email = prod.getRegister().getEmail();
        String firstname  = prod.getRegister().getFirstname();
        String lastname  = prod.getRegister().getLastname();
        String phone_number  = prod.getRegister().getPhone_number();
        Contact c = new Contact(firstname,lastname,email,phone_number);
        return c;
    }

    @Override
    public List<Product> getAllProducts(){
       return productRepo.findAll();
    }

    @Override
    public void deleteProductById(Long id){
        productRepo.deleteById(id);
    }

    @Override
    public Product getProductById(Long id){
        return productRepo.findById(id).get();

    }

    @Override
    public String sellProductById(Long id){  
        try{
            Product prod = getProductById(id);
            if(prod != null){
                Register register = prod.getRegister();
                    if(register.isUserLoggedIn() == true){
                        SoldProducts sold = new SoldProducts(prod.getProd_name(),prod.getProd_price(),prod.getLocation(),prod.getCategory(),prod.getDescription(),register);
                        soldProductsRepo.save(sold);
                        prod.setIsSold(true);
                        productRepo.save(prod);
                        deleteProductById(id); 

                        return "Product brought Successfully!";
                    }
                return "You are not loggedIn!";
            }else{
                return "Product Id is not valid!";
            }
        }catch(Exception e){
            return e.toString();
        }
    }
       
    
    @Override
    public List<SoldProducts> ExpiredProductList(){ 
        return soldProductsRepo.findAll();
    }

    @Override
    public List<Product> searchProducts(String query) {
        List<Product> products = productRepo.searchProductsRepo(query);
         return products;
    }

    @Override
    public List<Product> searchProductsByLocation(String query) {
        List<Product> products = productRepo.searchProductsLocRepo(query);
         return products;
    }

    @Override
    public List<Product> sortBypriceLToH() {
        List<Product> products = productRepo.sortProductsLToH();
         return products;
    }

    @Override
    public List<Product> sortBypriceHToL() {
        List<Product> products = productRepo.sortProductsHToL();
         return products;
    }
}


