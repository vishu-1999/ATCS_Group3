package com.atcs.olx.Repository.ProductRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atcs.olx.Entity.Products.SoldProducts;

@Repository
public interface SoldProductsRepo extends JpaRepository<SoldProducts,Long>{
    
}
