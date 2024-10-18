package com.api_rest.items_db_II.controllers;

import com.api_rest.items_db_II.domain.service.interfaces.ProductService;
import com.api_rest.items_db_II.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductControllerRest {

    private final ProductService productService;

    public ProductControllerRest(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable String id){
        return productService.getProduct(id);
    }

    @PostMapping("")
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping("/name/{name}")
    public Product getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @PutMapping("")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id){
        productService.deleteProductById(id);
    }

    @GetMapping("/list-product/{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name){
        List<Product> itemsList = productService.getProductsByName(name);
        if (itemsList.isEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Arrays.asList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(itemsList);
    }
}
