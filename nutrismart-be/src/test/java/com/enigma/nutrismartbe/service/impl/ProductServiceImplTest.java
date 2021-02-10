package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.enums.CategoryEnum;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.CategoryRepository;
import com.enigma.nutrismartbe.repository.ProductRepository;
import com.enigma.nutrismartbe.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    ProductService service;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        repository.deleteAll();
//    }

    @Test
    void saveProduct_shouldCreateOneProduct_whenInvoked() {
        Product product = new Product();
        product.setName("Sushi");
        service.saveProduct(product);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getProduct_shouldGetSpecificProductID() {
        Product product = new Product();
        product.setName("Sushi");
        repository.save(product);
        Product product1 = service.getProduct(product.getId());
        assertSame(product.getId(), product1.getId());
    }

    @Test
    void getProduct_shouldThrowException_whenIDNotFound(){
        Product product = new Product();
        product.setName("Sushi");
        repository.save(product);
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProduct("cow");
        });
    }

    @Test
    void getAllProduct_shouldGetTwoProducts_whenInvoked() {
        Product product = new Product();
        product.setName("Sushi");
        repository.save(product);

        Product product1 = new Product();
        product1.setName("Sashimi");
        repository.save(product1);

        Page<Product> products = service.getAllProduct(PageRequest.of(0,2));
        assertEquals(2, products.getTotalElements());
    }

    @Test
    void saveProductImage_shouldAbleToUploadImageAndRequestBody() throws IOException {
        String requestBody = "{\"name\":\"mkyong\"}";
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename.jpg", "jpg", "jpg".getBytes());
        service.saveProductImage(multipartFile, requestBody);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getProductsByCategory_shouldGetTwoProductsInsideACategory_whenInvoked() {

        Category category = new Category();
        List<Product> products = new ArrayList<>();
        category.setProducts(products);
        category.setCategoryName(CategoryEnum.FRUITS);
        categoryRepository.save(category);

        Product product = new Product();
        product.setCategory(category);
        repository.save(product);
        products.add(product);

        Product product1 = new Product();
        product1.setCategory(category);
        repository.save(product1);
        products.add(product1);

        category.setProducts(products);
        categoryRepository.save(category);

        Page<Product> productPage = service.getProductsByCategory(category.getId(), PageRequest.of(0,2));
        assertEquals(2, productPage.getTotalElements());
    }

    @Test
    void deleteProduct_shouldDeleteSpecificProduct_whenInvoked() {
        Product product = new Product();
        product.setName("Sushi");
        repository.save(product);

        service.deleteProduct(product.getId());
        assertFalse(repository.findById(product.getId()).isPresent());
    }
}