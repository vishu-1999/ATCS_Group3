package com.atcs.olx.ServiceImple.CartImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Products.Cart;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Repository.ProductRepo.CartRepo;
import com.atcs.olx.Repository.ProductRepo.ProductRepo;
import com.atcs.olx.Service.CartService.CartService;

@Component
public class CartServiceImple implements CartService{

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductRepo productRepo;


    Cart cart;

    @Override
    public String setCart(Register user,long prodId) {
        Product prod = productRepo.findById(prodId).get();
        if(prod != null){
            Cart c = new Cart(prod,user);
            cartRepo.save(c);
            return "Product added to cart!";
        }
        return "Product id is not valid!";
    }
    
}
