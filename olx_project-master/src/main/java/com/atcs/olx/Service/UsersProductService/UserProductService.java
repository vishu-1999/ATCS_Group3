package com.atcs.olx.Service.UsersProductService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Products.Contact;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Entity.Products.SoldProducts;

@Service
public interface UserProductService {
    public String addProduct(Product product);
    public List<Product> listProductByUser(Register register);
    public Contact getContactDetails(Product prod);
    public List<Product> getAllProducts();
    public void deleteProductById(Long id);
    public Product getProductById(Long id);
    public String sellProductById(Long id);
    public List<SoldProducts> ExpiredProductList();
    public List<Product> searchProducts(String query) ;
    public List<Product> searchProductsByLocation(String query);
    public List<Product> sortBypriceLToH();
    public List<Product> sortBypriceHToL();

}
