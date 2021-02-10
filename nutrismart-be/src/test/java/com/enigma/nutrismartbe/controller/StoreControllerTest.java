package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.Store;
import com.enigma.nutrismartbe.repository.StoreRepository;
import com.enigma.nutrismartbe.service.ProductService;
import com.enigma.nutrismartbe.service.StoreService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    StoreController controller;

    @Autowired
    StoreService service;

    @Autowired
    ProductService productService;

    @Autowired
    StoreRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void updateStore_shouldReturnOK_afterAbleToUpdateStoreInformation() throws Exception {
        List<Product> products = new ArrayList<>();

        Store store = new Store();
        store.setStoreName("Bakery");
        store.setAddress("Nowhere St.");
        store.setDetail("Street");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setProducts(products);
        service.saveStore(store);

        Product product = new Product();
        product.setName("Tellurium");
        productService.saveProduct(product);
        store.getProducts().add(product);

        Product product1 = new Product();
        product1.setName("Mercury");
        productService.saveProduct(product1);
        store.getProducts().add(product1);

        controller.updateStore(store);

        RequestBuilder builder = MockMvcRequestBuilders.put("/admin/store/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(store));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createBranch_shouldAbleToCreateAStore_beforeReturnStatusOK() throws Exception {
        Store store = new Store();
        store.setStoreName("Bakery");
        store.setAddress("Nowhere St.");
        store.setDetail("Street");

        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/store/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(store));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllStores_shouldReturnStatusOK_whenGetAllStores() throws Exception {
        Store store = new Store();
        store.setStoreName("Bakery");
        store.setAddress("Nowhere St.");
        store.setDetail("Street");
        service.saveStore(store);

        Store store1 = new Store();
        store1.setStoreName("Bakery");
        store1.setAddress("Nowhere St.");
        store1.setDetail("Street");
        service.saveStore(store1);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/store")
                .param("page","1").param("size","2").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getStore_shouldReturnStatusOK_whenGetSpecificStore() throws Exception {
        Store store = new Store();
        store.setStoreName("Bakery");
        store.setAddress("Nowhere St.");
        store.setDetail("Street");
        service.saveStore(store);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/store/"+store.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(store));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}