package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Voucher;
import com.enigma.nutrismartbe.repository.VoucherRepository;
import com.enigma.nutrismartbe.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherRepository repository;

    @Autowired
    VoucherService service;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void saveVoucher_shouldSaveOneVoucher_whenInvoked() {
        Voucher voucher =  new Voucher();
        voucher.setName("AKB48");
        voucher.setValue(0.5);
        service.saveVoucher(voucher);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void updateVoucher_shouldUpdateSpecificVoucher_whenInvoked() {
        Voucher voucher = new Voucher();
        voucher.setName("AKB48");
        voucher.setValue(0.5);
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        repository.save(voucher);

        Voucher newVoucher = repository.findById(voucher.getId()).get();
        newVoucher.setName("JKT48");
        service.updateVoucher(newVoucher);

        assertNotSame(voucher, newVoucher);
    }

    @Test
    void getVoucher_shouldGetSpecificVoucherByName_whenInvoked() {
        Voucher voucher = new Voucher();
        voucher.setName("AKB48");
        voucher.setValue(0.5);
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        repository.save(voucher);

        Voucher find = service.getVoucher(voucher.getName());
        assertSame(find.getName(), voucher.getName());
    }

    @Test
    void getAllVouchers_shouldReturnTwoVouchers_whenInvoked() {
        Voucher voucher = new Voucher();
        voucher.setName("AKB48");
        voucher.setValue(0.5);
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        repository.save(voucher);

        Voucher voucher1 = new Voucher();
        voucher1.setName("AKB48");
        voucher1.setValue(0.5);
        voucher1.setValid(new Timestamp(System.currentTimeMillis()));
        repository.save(voucher1);

        Page<Voucher> vouchers = service.getAllVouchers(PageRequest.of(0,2));
        assertEquals(2, vouchers.getTotalElements());
    }

    @Test
    void deleteVoucher_shouldReturnNull_whenReturn() {
        Voucher voucher = new Voucher();
        voucher.setName("AKB48");
        voucher.setValue(0.5);
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        repository.save(voucher);

        service.deleteVoucher(voucher.getId());

        assertFalse(repository.findById(voucher.getId()).isPresent());
    }
}