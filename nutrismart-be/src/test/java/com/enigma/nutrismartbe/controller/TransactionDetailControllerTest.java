package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.TransactionDetail;
import com.enigma.nutrismartbe.repository.TransactionDetailRepository;
import com.enigma.nutrismartbe.service.ProductService;
import com.enigma.nutrismartbe.service.TransactionDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionDetailControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    TransactionDetailService service;

    @Autowired
    TransactionDetailRepository repository;

    @Autowired
    ProductService productService;

    @Autowired
    TransactionDetailController controller;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void saveDetail_shouldReturnStatusOK_whenSuccessfullySaveNewDetail() throws Exception {
        Product product = new Product();
        product.setName("Pork");
        product.setPrice(50000);
        productService.saveProduct(product);

        TransactionDetail detail = new TransactionDetail();
        detail.setSubTotal(product.getPrice());
        detail.setProduct(product);
        detail.setQuantity(5);
        detail.setPrice(product.getPrice());

        RequestBuilder builder = MockMvcRequestBuilders.post("/transaction-detail/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(detail));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}