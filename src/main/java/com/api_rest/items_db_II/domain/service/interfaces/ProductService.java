package com.api_rest.items_db_II.domain.service.interfaces;

import com.api_rest.items_db_II.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(String id);

    Product saveProduct(Product product);

    Product getProductByName(String name);

    List<Product> getProductsByName(String name);

    Product updateProduct(Product product);

    void deleteProductById(String id);
}
