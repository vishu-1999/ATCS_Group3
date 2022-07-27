package com.atcs.olx.Repository.ProductRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.atcs.olx.Entity.Products.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " + "p.prod_name LIKE CONCAT('%', :query, '%')" +
            "Or p.prod_price LIKE CONCAT('%', :query, '%')"
    )
    List<Product> searchProductsRepo(String query);

    @Query(value = "SELECT * from Product where location_id IN (select id from location where city like CONCAT('%', :query, '%'))",nativeQuery = true)
    List<Product> searchProductsLocRepo(String query);

    @Query(value = "SELECT * from Product order by prod_price ASC ",nativeQuery = true)
    List<Product> sortProductsLToH();

    @Query(value = "SELECT * from Product order by prod_price DESC ",nativeQuery = true)
    List<Product> sortProductsHToL();

}
