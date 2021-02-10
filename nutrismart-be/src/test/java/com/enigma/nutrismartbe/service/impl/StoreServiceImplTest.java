package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.Store;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.StoreRepository;
import com.enigma.nutrismartbe.service.ProductService;
import com.enigma.nutrismartbe.service.StoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceImplTest {

    @Autowired
    StoreRepository repository;

    @Autowired
    StoreService service;

    @Autowired
    ProductService productService;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        repository.deleteAll();
//    }

    @Test
    void saveStore_shouldSaveOneStore_whenInvoked() {
        Store store = new Store();
        store.setAddress("a");
        store.setDetail("b");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setStoreName("c");
        service.saveStore(store);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void updateStore_shouldUpdateSpecificStore_whenInvoked() {

        Store store = new Store();
        repository.save(store);

        Store newStore = repository.findById(store.getId()).get();

        List<Product> products = new ArrayList<>();

        newStore.setProducts(products);
        service.updateStore(newStore);

        assertNotEquals(store, newStore);
    }

    @Test
    void getStore_shouldGetSpecificStoreID() {
        Store store = new Store();
        store.setAddress("a");
        store.setDetail("b");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setStoreName("c");
        repository.save(store);

        Store store1 = service.getStore(store.getId());
        assertEquals(store.getId(), store1.getId());
    }

    @Test
    void getStore_shouldThrowException_whenCallingAWrongStoreID(){
        Store store = new Store();
        store.setAddress("a");
        store.setDetail("b");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setStoreName("c");
        repository.save(store);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getStore("cow");
        });
    }

    @Test
    void getStores_shouldGetTwoStores_whenInvoked() {
        Store store = new Store();
        store.setAddress("a");
        store.setDetail("b");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setStoreName("c");
        repository.save(store);

        Store store1 = new Store();
        store1.setAddress("b");
        store1.setDetail("c");
        store1.setLatitude(0.2);
        store1.setLongitude(0.1);
        store1.setStoreName("d");
        repository.save(store1);

        Page<Store> stores = service.getStores(PageRequest.of(0,2));
        assertEquals(2, stores.getTotalElements());
    }

    @Test
    void deleteStore_shouldDeleteSpecificStoreByID() {
        Store store = new Store();
        store.setAddress("a");
        store.setDetail("b");
        store.setLatitude(0.0);
        store.setLongitude(0.0);
        store.setStoreName("c");
        repository.save(store);

        service.deleteStore(store.getId());
        assertFalse(repository.findById(store.getId()).isPresent());
    }
}