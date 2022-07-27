package com.atcs.olx.Controller.UserProductController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atcs.olx.Controller.AuthenticateController.Authenticate;
import com.atcs.olx.Entity.Authenticate.Register;
import com.atcs.olx.Entity.Products.Contact;
import com.atcs.olx.Entity.Products.ListProductByEmail;
import com.atcs.olx.Entity.Products.Product;
import com.atcs.olx.Entity.Products.SoldProducts;
import com.atcs.olx.Service.CartService.CartService;
import com.atcs.olx.Service.ServiceAuthenticate.ServiceAuthenticate;
import com.atcs.olx.Service.UsersProductService.UserProductService;

@RestController
@RequestMapping("/olx")
public class UsersProduct {
    @Autowired
    ServiceAuthenticate serviceUsers;

    @Autowired
    UserProductService userProductService;

    @Autowired
    CartService cartService;

    String msg = "";

    // Add a product by user
    @PostMapping("/user/add_product")
    public ResponseEntity<String> addProductByUser(@RequestBody Product product) {
        try {
            if (Authenticate.checkUser == true) {
                userProductService.addProduct(product);
                msg = "Product added successfully!";
                return new ResponseEntity<String>(msg, HttpStatus.OK);
            } else {
                msg = "Authentication error!! Please login";
                return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
            }

        } catch (Exception e) {
            msg = "Exception error!!";
            return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
        }

    }

    // list product of a user
    @GetMapping("/user/list_product")
    public ResponseEntity<?> listProductByUser(@RequestBody ListProductByEmail listProductByEmail) {
        List<Register> allUsers = serviceUsers.getAllUsers();
        for (Register a : allUsers) {
            if (a.getEmail().equals(listProductByEmail.getEmail())) {
                return new ResponseEntity<List<Product>>(userProductService.listProductByUser(a), HttpStatus.OK);
            }
        }
        msg = " email not found!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);

    }

    // Get Contact details of any product
    @GetMapping("/user/get/contact_details_of_any_product/{id}")
    public ResponseEntity<?> contactDetails(@PathVariable("id") long id) {
        List<Product> products = userProductService.getAllProducts();
        for (Product a : products) {
            if (a.getId().equals(id)) {
                return new ResponseEntity<Contact>(userProductService.getContactDetails(a), HttpStatus.OK);

            }
        }
        msg = "Id is not associated with any product!";
        return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
    }

    // List all the products available
    @GetMapping("/user/list_all_product")
    public ResponseEntity<?> getAllProduct() {
        List<Product> prod = userProductService.getAllProducts();
        if (prod.isEmpty()) {
            msg = "No product available";
            return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
        } else {
            return new ResponseEntity<List<Product>>(prod, HttpStatus.OK);
        }
    }

    // Delete a product by using product id
    @DeleteMapping("/user/delete_product/{id}")
    public ResponseEntity<String> deleteProdByUser(@PathVariable("id") long id, @RequestBody ListProductByEmail email) {
        List<Register> users = serviceUsers.getAllUsers();
        for (Register r : users) {
            if (r.getEmail().equals(email.getEmail())) {
                List<Product> prod = userProductService.listProductByUser(r);
                for (Product p : prod) {
                    if (p.getId().equals(id)) {
                        userProductService.deleteProductById(id);
                        msg = "Product deleted successfully!";
                        return new ResponseEntity<String>(msg, HttpStatus.OK);
                    }
                }
                msg = "Invalid Product Id!";
                return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
            }

        }
        msg = "Invalid User!";
        return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
    }

    // Add a product to cart using product id
    @PostMapping("/user/add_product_to_bookmark/{id}")
    public ResponseEntity<String> addProductToBookmark(@PathVariable("id") long prodId) {
        try {
            List<Register> users = serviceUsers.getAllUsers();
            for (Register user : users) {
                if (user.isUserLoggedIn() == true) {
                    msg = cartService.setCart(user, prodId);
                    return new ResponseEntity<String>(msg, HttpStatus.OK);
                }
            }
            msg = "Please logIn first!";
            return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            msg = "No any product found with this id!";
            return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
        }

    }

    // Buy any product using product id
    @PostMapping("/user/buy_product/{id}")
    public ResponseEntity<String> buyProduct(@PathVariable("id") long id) {
        try {
            if (Authenticate.checkUser == true) {
                Product prod = userProductService.getProductById(id);
                if (prod != null) {
                    msg = userProductService.sellProductById(id);
                    return new ResponseEntity<String>(msg, HttpStatus.OK);
                } else {
                    msg = "Product Id is not valid to buy!";
                    return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
                }
            } else {
                msg = "Please logIn first!";
                return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            msg = "Product Id is not valid!";
            return new ResponseEntity<String>(msg, HttpStatus.BAD_GATEWAY);
        }
    }

    // Admin can list all the expired product
    @GetMapping("/admin/list_of_expired_product")
    public ResponseEntity<?> listOfExpiredItems() {
        try {
            if (Authenticate.checkAdmin == true) {
                return new ResponseEntity<List<SoldProducts>>(userProductService.ExpiredProductList(), HttpStatus.OK);
            } else {
                msg = "Please login as an admin";
                return new ResponseEntity<String>(msg,HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // User can search product by product name
    @GetMapping("/user/search_product_byName")
    public ResponseEntity<?> searchProducts(@RequestParam("name") String name) {
        try {
            return new ResponseEntity<List<Product>>(userProductService.searchProducts(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // User can search product by product price
    @GetMapping("/user/search_product_byPrice")
    public ResponseEntity<?> searchProductsPrice(@RequestParam("price") String price) {
        try {
            return new ResponseEntity<List<Product>>(userProductService.searchProducts(price), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // User can search product by location
    @GetMapping("/user/search_product_byLocation")
    public ResponseEntity<?> searchProductsLocation(@RequestParam("location") String location) {
        try {
            return new ResponseEntity<List<Product>>(userProductService.searchProductsByLocation(location), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // Filter products on the basis of price (Low to High)
    @GetMapping("/user/sort_productByPrice_low_to_high")
    public ResponseEntity<?> sortProductByPrice_L_H() {
        try {
            return new ResponseEntity<List<Product>>(userProductService.sortBypriceLToH(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

    // Filter products on the basis of price (High to Low)
    @GetMapping("/user/sort_productByPrice_high_to_low")
    public ResponseEntity<?> sortProductByPrice_H_L() {
        try {
            return new ResponseEntity<List<Product>>(userProductService.sortBypriceHToL(),HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<String>(e.toString(),HttpStatus.BAD_GATEWAY);
        }
    }

}
