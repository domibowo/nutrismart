package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.TransactionDetail;
import com.enigma.nutrismartbe.repository.ProductRepository;
import com.enigma.nutrismartbe.service.CategoryService;
import com.enigma.nutrismartbe.service.ProductService;
import com.enigma.nutrismartbe.util.FileUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    ProductController controller;

    @Autowired
    ProductService service;

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FileUtility utility;

    @Autowired
    MockMvc mvc;

//    @BeforeEach
//    void cleanUp(){
//        repository.deleteAll();
//    }

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getProduct_shouldReturnStatusOK_whenSuccessfullyGetSpecificProductById() throws Exception {
        List<TransactionDetail> details = new ArrayList<>();

        Product product = new Product();
        product.setName("Beef");
        product.setStock(12);
        product.setPrice(50000);
        product.setMinBundle("100 grams");
        product.setDetails(details);
        service.saveProduct(product);

        controller.getProduct(product.getId());

        RequestBuilder builder = MockMvcRequestBuilders.get("/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(product));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveProduct_shouldReturnStatusOK_whenSuccessfullyCreateNewProduct() throws IOException {
//        Product product = new Product();
//        product.setName("Beef");
//        product.setStock(12);
//        product.setPrice(50000);
//        product.setMinBundle("100 grams");
//
//        String requestBody = "{\"name\":\"mkyong\"}";
//        MultipartFile file = new MockMultipartFile("filename", "filename.jpg", "jpg", "jpg".getBytes());
//        controller.saveProduct(file, requestBody);
//
//        RequestBuilder builder = MockMvc
    }

    @Test
    void updateProduct_shouldReturnStatusOK_whenSuccessfullyUpdatedAProduct() throws Exception {
        Product product = new Product();
        product.setName("Oxygen");
        product.setStock(12);
        product.setPrice(50000);
        product.setMinBundle("100 grams");
        service.saveProduct(product);

        product.setName("Hydrogen");
        product.setStock(15);
        controller.updateProduct(product);

        RequestBuilder builder = MockMvcRequestBuilders.put("/admin/product/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(product));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllProduct() throws Exception {
        Product product = new Product();
        product.setName("Oxygen");
        product.setStock(12);
        product.setPrice(50000);
        product.setMinBundle("100 grams");
        service.saveProduct(product);

        Product product1 = new Product();
        product1.setName("Potassium");
        product1.setStock(12);
        product1.setPrice(50000);
        product1.setMinBundle("100 grams");
        service.saveProduct(product1);

        controller.getAllProduct(1,2);

        RequestBuilder builder = MockMvcRequestBuilders.get("/product")
                .param("page", "1").param("size", "2")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getProductsByCategory_shouldReturnStatusOK_afterSuccessfullyGetProductsByCategory() throws Exception {

        List<Product> products = new ArrayList<>();
        Category category = new Category();
        category.setProducts(products);
        categoryService.saveCategory(category);

        Product product = new Product();
        product.setName("Onion");
        product.setCategory(category);
        service.saveProduct(product);

        Product product1 = new Product();
        product1.setName("Garlic");
        product1.setCategory(category);
        service.saveProduct(product1);

        controller.getProductsByCategory(category.getId(), 1, 2);
        RequestBuilder builder = MockMvcRequestBuilders.get("/product/category/"+category.getId())
                .param("page","1").param("size", "2").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteProduct_shouldReturnStatusOK_afterSuccessfullyDeleteAProduct() throws Exception {
        Product product = new Product();
        product.setName("Truffle");
        service.saveProduct(product);

        RequestBuilder builder = MockMvcRequestBuilders.delete("/admin/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(product));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getProductPhoto_shouldReturnStatusOK_afterSuccessfullyReadAFile() {

    }
}