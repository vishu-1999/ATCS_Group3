package com.atcs.olx.Entity.Products;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atcs.olx.Entity.Authenticate.Register;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@EnableTransactionManagement
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String prod_name;

    @Column(nullable = false)
    private BigDecimal prod_price;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isSold = false;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Register register;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    public Product(Long id, String prod_name, BigDecimal prod_price, Location location, Category category,
            String description, LocalDateTime date, boolean isSold, Register register) {
        this.id = id;
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.location = location;
        this.category = category;
        this.description = description;
        this.date = date;
        this.isSold = isSold;
        this.register = register;
    
    }


    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public BigDecimal getProd_price() {
        return prod_price;
    }

    public void setProd_price(BigDecimal prod_price) {
        this.prod_price = prod_price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_date() {
        return date;
    }

    public void setCreated_date(LocalDateTime date) {
        this.date = date;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

}
