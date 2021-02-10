package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Voucher;
import com.enigma.nutrismartbe.repository.VoucherRepository;
import com.enigma.nutrismartbe.service.VoucherService;
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

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoucherControllerTest {

    @Autowired
    VoucherService service;

    @Autowired
    VoucherRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    VoucherController controller;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void saveVoucher_shouldReturnStatusOK_afterSuccessfullyAddNewVoucher() throws Exception {
        Voucher voucher = new Voucher();
        voucher.setName("Fire");
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        voucher.setValue(0.95);

        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/voucher/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(voucher));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateVoucher_whenSuccessfullyUpdateAVoucher_shouldReturnStatusOK() throws Exception {
        Voucher voucher = new Voucher();
        voucher.setName("Earth");
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        voucher.setValue(0.95);
        service.saveVoucher(voucher);

        voucher.setValue(0.05);

        RequestBuilder builder = MockMvcRequestBuilders.put("/admin/voucher/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(voucher));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllActiveVouchers_whenSuccessfullyGetAllVouchers_shouldReturnStatusOK() throws Exception {
        Voucher voucher = new Voucher();
        voucher.setName("Earth");
        voucher.setValid(new Timestamp(System.currentTimeMillis()));
        voucher.setValue(0.95);
        service.saveVoucher(voucher);

        Voucher voucher1 = new Voucher();
        voucher1.setName("Earth");
        voucher1.setValid(new Timestamp(System.currentTimeMillis()));
        voucher1.setValue(0.95);
        service.saveVoucher(voucher);

        RequestBuilder builder = MockMvcRequestBuilders.get("/voucher")
                .param("page","1").param("size", "2").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}