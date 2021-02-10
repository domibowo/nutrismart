package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.ProductDetail;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.ProductRepository;
import com.enigma.nutrismartbe.service.ProductDetailService;
import com.enigma.nutrismartbe.service.ProductService;
import com.enigma.nutrismartbe.util.impl.FileUtilityImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailServiceImpl productDetailService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    FileUtilityImp fileUtility;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(String id) {
        Product product;
        if (productRepository.findById(id).isPresent()){
            product = productRepository.findById(id).get();
        }else throw new ResourceNotFoundException(id, Product.class);
        return product;
    }


    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products;
    }

    @Override
    public Product saveProductImage(MultipartFile multipartFile, String requestBody) throws IOException {
        Product product = saveProduct(objectMapper.readValue(requestBody, Product.class));
        String destination = String.format("%s.%s",product.getId().replaceAll("-",""), FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        String path = fileUtility.store(multipartFile,destination);
        product.setPhoto(path);
        ProductDetail productDetail = new ProductDetail();
        productDetailService.saveProductDetail(productDetail);
        product.setProductDetail(productDetail);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getProductsByCategory(String id, Pageable pageable) {
        Category category = categoryService.getCategory(id);
        Page<Product> products = productRepository.findAllByCategory(category, pageable);
        return products;
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
