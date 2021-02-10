package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.*;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.enigma.nutrismartbe.repository.TransactionRepository;
import com.enigma.nutrismartbe.service.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionService service;

    @Autowired
    VoucherService voucherService;

    @Autowired
    TransactionDetailService detailService;

    @Autowired
    TransactionController controller;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getTransaction_shouldReturnStatusOK_whenSuccessfullyGetSpecificTransactionByID() throws Exception {
        Transaction transaction = new Transaction();
        repository.save(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.get("/account/transaction/"+transaction.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void saveTransaction_shouldReturnStatusOK_whenSuccessfullySaveNewTransaction() throws Exception {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("anakin");
        account.setEmail("anakin_skywalker@mail.com");
        account.setPassword("anakin");
        accountService.saveAccount(account);

        Product product = new Product();
        product.setName("Bakwan");
        product.setPrice(4);
        productService.saveProduct(product);

        Transaction transaction = new Transaction();
        transaction.setProductQty(4);
        transaction.setProductId(product.getId());
        transaction.setAccountId(account.getId());
        service.saveTransaction(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.post("/account/transaction/cart")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void setVoucher_shouldReturnStatusOK_whenSuccessfullySetVoucherToATransaction() throws Exception {
        List<TransactionDetail> details = new ArrayList<>();

        Voucher voucher = new Voucher();
        voucher.setName("Nitroglycerin");
        voucher.setValue(0.5);
        voucherService.saveVoucher(voucher);

        TransactionDetail detail = new TransactionDetail();
        detail.setSubTotal(20000);
        detailService.saveDetail(detail);

        Transaction transaction = new Transaction();
        transaction.setTransactionDetails(details);
        transaction.setTrxDate(new Timestamp(System.currentTimeMillis()));
        repository.save(transaction);

        Transaction transaction1 = repository.findById(transaction.getId()).get();
        detail.setTransaction(transaction1);
        transaction1.getTransactionDetails().add(detail);
        transaction1.setGrandTotal(detail.getSubTotal());
        transaction1 = repository.save(transaction1);
        transaction1.setVoucherName(voucher.getName());

        RequestBuilder builder = MockMvcRequestBuilders.put("/account/transaction/cart/voucher/"+transaction1.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction1));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void commitTransaction_shouldReturnStatusOK_whenSuccessfullyCommitATransaction() throws Exception {
        List<TransactionDetail> details = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setTransactionDetails(details);
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction);

        Product product = new Product();
        product.setName("Coco");
        product.setStock(10);
        product.setPrice(4);
        productService.saveProduct(product);

        TransactionDetail detail = new TransactionDetail();
        detail.setProduct(product);
        detail.setQuantity(2);
        detailService.saveDetail(detail);

        List<TransactionDetail> details1 = transaction.getTransactionDetails();
        details1.add(detail);
        repository.save(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.put("/account/transaction/purchased/"+transaction.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void removeProduct_shouldReturnStatusOK_whenSuccessfullyRemoveDetailFromTransaction() throws Exception {
        Transaction transaction = new Transaction();

        TransactionDetail detail = new TransactionDetail();
        detail.setSubTotal(200);
        List<TransactionDetail> details = new ArrayList<>();
        details.add(detail);

        transaction.setTransactionDetails(details);
        transaction.setGrandTotal(20000);
        repository.save(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.put("/account/transaction/cart/"+detail.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCart_shouldGetSpecificCartOfSpecificAccount_thenReturnStatusOK() throws Exception {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("halloween");
        account.setEmail("halloween@directors-cut.com");
        account.setPassword("halloween");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setAccount(account);
        repository.save(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.get("/account/transaction/status/"+account.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTransactionsById_shouldReturnStatusOK_afterGetAllTransactionsByAccountID() throws Exception {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setAccount(account);
        repository.save(transaction1);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/transaction/"+account.getId())
                .param("page","1").param("size","2").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(account));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTransactionsByStatus_shouldReturnStatusOK_whenAbleToGetAllTransactionsByTransactionStatus() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction1);

        Transaction find = new Transaction();
        find.setStatusEnum(TransactionStatusEnum.CART);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/transaction/status")
                .param("page","1").param("size","2").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(find));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCartsofAnAccount_shouldReturnStatusOK_whenAbleToGetCartsOfAnAccount() throws Exception {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("Spider-man");
        account.setEmail("spider_man@mail.com");
        account.setPassword("spiderman");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setAccount(account);
        repository.save(transaction);

        RequestBuilder builder = MockMvcRequestBuilders.get("/account/transaction/cart/"+account.getId())
                .param("page","1").param("size", "1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getHistories_shouldReturnStatusOK_whenAbleToGetAll() throws Exception {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("Gandalf");
        account.setEmail("gandalf@mail.com");
        account.setPassword("gandalfTheWhite");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.PURCHASED);
        transaction.setAccount(account);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setStatusEnum(TransactionStatusEnum.PURCHASED);
        transaction1.setAccount(account);
        repository.save(transaction1);

        RequestBuilder builder = MockMvcRequestBuilders.get("/account/transaction/history/"+account.getId())
                .param("page","1").param("size","2").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateCart_shouldReturnStatusOK_whenSuccessfullyUpdateCart() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setTransactionDetails(new ArrayList<>());
        repository.save(transaction);

        Transaction target = repository.findById(transaction.getId()).get();
        target.setTrxDate(new Timestamp(System.currentTimeMillis()));

        RequestBuilder builder = MockMvcRequestBuilders.put("/account/transaction/update")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(target));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}