package org.example.Controller;

import org.example.Objects.Product;
import org.example.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> allProducts() {
        List<Product> allProducts = productService.allProducts();
        return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Product product =productService.getProductById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/find-product-by-name")
    public ResponseEntity<List<Product>> findProductByName(@RequestParam String name) {
        List<Product> products =productService.findProductByName(name);
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") String id, @RequestBody Product product) {
        Product updatedProduct =productService.updateProductById(id,product);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") String id) {
        productService.deleteProductById(id);
        return new ResponseEntity<String>("Ürün Silindi", HttpStatus.OK);
    }
}
