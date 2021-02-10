package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    public Product saveProduct(Product product);
    public Product getProduct(String id);
    public void deleteProduct(String id);
    public Page<Product> getAllProduct(Pageable pageable);
    public Product saveProductImage(MultipartFile multipartFile, String requestBody) throws IOException;
    public Page<Product> getProductsByCategory(String id, Pageable pageable);
}
