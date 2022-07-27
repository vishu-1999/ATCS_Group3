package com.atcs.olx.Entity.Authenticate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.atcs.olx.Entity.Messages.AllMessages;
import com.atcs.olx.Entity.Products.Cart;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Entity.Products.SoldProducts;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.List; 


@Entity
@Table(name = "register")
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , length = 45)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false, length = 25)
    private String firstname;

    @Column(nullable = false, length = 25)
    private String lastname;

    @Column(nullable = false, unique = true, length = 25)
    private String phone_number;

    @Column(nullable = false)
    private LocalDateTime created_date = LocalDateTime.now(); 

    @Column(nullable = false)
    private boolean isUserLoggedIn = false;

    @OneToMany(mappedBy = "register", cascade = CascadeType.ALL)
    private List<Product> product;

    @OneToMany(mappedBy = "register", cascade = CascadeType.ALL)
    private List<SoldProducts> soldProduct;

    @OneToMany(mappedBy = "register", cascade = CascadeType.ALL)
    private List<AllMessages> allMessages;


    @OneToOne(mappedBy = "register",cascade = CascadeType.ALL)
    private Cart cart;

    public Register(Long id, String email, String password, String firstname, String lastname, String phone_number,
            LocalDateTime created_date, boolean isUserLoggedIn, List<Product> product, Cart cart, List<AllMessages> allMessages) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone_number = phone_number;
        this.created_date = created_date;
        this.isUserLoggedIn = isUserLoggedIn;
        this.product = product;
        this.cart = cart;
        this.allMessages = allMessages;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }



    public Register() {
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        this.isUserLoggedIn = isUserLoggedIn;
    }


    @JsonManagedReference
    public List<Product> getProduct() {
        return product;
    }
    
    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<AllMessages> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<AllMessages> allMessages) {
        this.allMessages = allMessages;
    }
    
}
