package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.ProductDetail;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.ProductDetailRepository;
import com.enigma.nutrismartbe.service.ProductDetailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDetailServiceImplTest {

    @Autowired
    ProductDetailService detailService;

    @Autowired
    ProductDetailRepository repository;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        repository.deleteAll();
//    }

    @Test
    void saveProductDetail_shouldSaveOneDetail_whenInvoked() {
        ProductDetail detail = new ProductDetail();
        detail.setCalorie("aaa");
        detail.setDescription("bbb");
        detail.setNutritionFact("ccc");
        detailService.saveProductDetail(detail);

        assertTrue(repository.findAll().size()==1);
    }

    @Test
    void getProductDetail_shouldGetSpecificDetailID_whenInvoked() {
        ProductDetail detail = new ProductDetail();
        detail.setCalorie("aaa");
        detail.setDescription("bbb");
        detail.setNutritionFact("ccc");
        repository.save(detail);
        ProductDetail find = detailService.getProductDetail(detail.getId());
        assertSame(detail.getId(), find.getId());
    }

    @Test
    void getProductDetail_shouldThrowException_whenDetailIDNotFound(){
        ProductDetail detail = new ProductDetail();
        detail.setCalorie("aaa");
        detail.setDescription("bbb");
        detail.setNutritionFact("ccc");
        repository.save(detail);
        assertThrows(ResourceNotFoundException.class, () -> {
            detailService.getProductDetail("cow");
        });
    }

    @Test
    void deleteProductDetail_shouldDeleteSpecificDetail_whenInvoked() {
        ProductDetail detail = new ProductDetail();
        detail.setCalorie("aaa");
        detail.setDescription("bbb");
        detail.setNutritionFact("ccc");
        repository.save(detail);

        detailService.deleteProductDetail(detail.getId());
        assertFalse(repository.findById(detail.getId()).isPresent());
    }

    @Test
    void getAllProductDetail_shouldGetTwoDetails_whenInvoked() {
        ProductDetail detail = new ProductDetail();
        detail.setCalorie("aaa");
        detail.setDescription("bbb");
        detail.setNutritionFact("ccc");
        repository.save(detail);

        ProductDetail detail1 = new ProductDetail();
        detail1.setCalorie("bbb");
        detail1.setDescription("ccc");
        detail1.setNutritionFact("ddd");
        repository.save(detail1);

        Page<ProductDetail> details = detailService.getAllProductDetail(PageRequest.of(0,2));
        assertEquals(2, details.getTotalElements());
    }
}