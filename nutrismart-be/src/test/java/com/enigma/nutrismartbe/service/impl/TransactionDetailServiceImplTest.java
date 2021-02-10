package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Transaction;
import com.enigma.nutrismartbe.entity.TransactionDetail;
import com.enigma.nutrismartbe.repository.TransactionDetailRepository;
import com.enigma.nutrismartbe.service.TransactionDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionDetailServiceImplTest {

    @Autowired
    TransactionDetailService service;

    @Autowired
    TransactionDetailRepository repository;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void saveDetail_shouldCreateOneDetail_whenInvoked() {
        TransactionDetail detail = new TransactionDetail();
        detail.setQuantity(5);
        service.saveDetail(detail);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getAllDetails_shouldReturnTwoDetails_whenInvoked() {
        TransactionDetail detail = new TransactionDetail();
        detail.setQuantity(5);
        repository.save(detail);

        TransactionDetail detail1 = new TransactionDetail();
        detail1.setQuantity(5);
        repository.save(detail1);

        Page<TransactionDetail> details = service.getAllDetails(PageRequest.of(0,2));
        assertEquals(2, details.getTotalElements());
    }

    @Test
    void deleteDetail_shouldDeleteSpecificDetailByID_whenInvoked() {
        TransactionDetail detail = new TransactionDetail();
        detail.setQuantity(5);
        repository.save(detail);

        service.deleteDetail(detail.getId());
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void getSpecificDetail_shouldGetSpecificDetailByID() {
        TransactionDetail detail = new TransactionDetail();
        detail.setQuantity(5);
        repository.save(detail);

        TransactionDetail find = service.getSpecificDetail(detail.getId());
        assertSame(detail.getId(), find.getId());
    }
}