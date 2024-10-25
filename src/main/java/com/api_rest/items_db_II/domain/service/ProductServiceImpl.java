package com.api_rest.items_db_II.domain.service;

import com.api_rest.items_db_II.domain.repository.ProductRepository;
import com.api_rest.items_db_II.domain.service.interfaces.ProductService;
import com.api_rest.items_db_II.exeptions.ExceptionHandler;
import com.api_rest.items_db_II.exeptions.ThrowFlush;
import com.api_rest.items_db_II.funtionalinterfaces.LambdaSupplierExeption;
import com.api_rest.items_db_II.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private RedisTemplate<String,Object> redisTemplateProduct;

    private final String CACHE_PREFIX = "product_";

    private final LambdaSupplierExeption lambdaSupplierExeption = new ExceptionHandler();

    private ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplateProduct, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.redisTemplateProduct = redisTemplateProduct;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(String id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id: " + id + " No encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name).orElseThrow(() -> new IllegalArgumentException("Producto con el nombre : " + name + " no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByName(String name) {
        // Intentar obtener de Redis
        String redisKey = CACHE_PREFIX + name;
        String listObj = (String) redisTemplateProduct.opsForValue().get(redisKey);
        System.out.println(listObj);
        if (listObj != null) {
            try {
                List<Product> productListFromRedis = objectMapper.readValue(listObj, new TypeReference<List<Product>>() {
                });
                System.out.println("ENTRO llevar data de redis");
                return productListFromRedis;
            } catch (JsonProcessingException e) {
                System.out.println(e);
            }

        }

        List<Product> productListMongo = productRepository.getProductsByName(name);
        if (!productListMongo.isEmpty()){
            // Serializar la lista de productos a un JSON array
            try {
                String json = objectMapper.writeValueAsString(productListMongo);

                redisTemplateProduct.opsForValue().set(redisKey, json);

                redisTemplateProduct.expire(redisKey, 10, TimeUnit.MINUTES);
            }catch (JsonProcessingException e){

            }
        }
        return productListMongo;
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
