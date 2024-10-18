package com.api_rest.items_db_II.domain.service;

import com.api_rest.items_db_II.domain.repository.ProductRepository;
import com.api_rest.items_db_II.domain.service.interfaces.ProductService;
import com.api_rest.items_db_II.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(String id){
        return productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Id: "+id+" No encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductByName(String name){
        return productRepository.getProductByName(name).orElseThrow(()-> new IllegalArgumentException("Producto con el nombre : "+name+" no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByName(String name) {
        return productRepository.getProductsByName(name);
    }

    @Override
    public Product saveProduct(Product product){
        Product productClone = new Product(product.getName(),product.getPrice());
        return productRepository.save(productClone);
    }

    @Override
    public Product updateProduct(Product product) {

        Product productDb = productRepository.findById(product.getId()).orElseThrow();
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        return productRepository.save(productDb);
    }

    public void deleteProductById(String id){
        productRepository.deleteById(id);
    }


}
