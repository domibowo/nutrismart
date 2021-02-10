package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.*;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.enigma.nutrismartbe.exception.InactiveAccountException;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.exception.VoucherInvalidException;
import com.enigma.nutrismartbe.repository.TransactionRepository;
import com.enigma.nutrismartbe.repository.VoucherRepository;
import com.enigma.nutrismartbe.service.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionService service;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionDetailService detailService;

    @Autowired
    ProductService productService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void saveTransaction_shouldCreateOneNewTransaction() {

        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("domibowo");
        account.setEmail("domibowo_bow@mail.co");
        account.setPassword("domiskywalker");
        accountService.saveAccount(account);

        Product product = new Product();
        product.setName("Sushi");
        product.setPrice(4);
        productService.saveProduct(product);

        Transaction transaction = new Transaction();
        transaction.setProductQty(4);
        transaction.setProductId(product.getId());
        transaction.setAccountId(account.getId());
        service.saveTransaction(transaction);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void saveTransaction_shouldAddNewProductOnTheSameTransactionID(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("JohnDoe");
        account.setEmail("john_doe@mail.com");
        account.setPassword("john_doe");
        accountService.saveAccount(account);

        Product product = new Product();
        product.setName("Almond");
        product.setPrice(4);
        productService.saveProduct(product);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setProductQty(4);
        transaction.setProductId(product.getId());
        transaction.setAccountId(account.getId());
        repository.save(transaction);

        Product product1 = new Product();
        product1.setName("Banana");
        product1.setPrice(4);
        productService.saveProduct(product1);

        Transaction transaction1 = repository.findById(transaction.getId()).get();

        transaction1.setAccountId(account.getId());
        transaction1.setProductId(product1.getId());
        transaction1.setProductQty(5);

        service.saveTransaction(transaction1);

        assertSame(transaction.getId(), transaction1.getId());
    }

    @Test
    void saveTransaction_shouldNowBeAbleForInactiveAccounts(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.INACTIVE);
        account.setUserName("JaneDoe");
        account.setEmail("jane_doe@mail.net");
        account.setPassword("jane_doe");
        accountService.saveAccount(account);

        Product product = new Product();
        product.setName("Potato");
        product.setPrice(4);
        productService.saveProduct(product);

        Account account1 = accountService.deleteAccount(account.getId());

        Transaction transaction = new Transaction();
        transaction.setProductQty(4);
        transaction.setProductId(product.getId());
        transaction.setAccountId(account1.getId());

        assertThrows(InactiveAccountException.class, () -> {
            service.saveTransaction(transaction);
        });
    }

    @Test
    void removeProduct_shouldReturnZero_whenRemoveSpecificTransactionDetail() {
        Transaction transaction = new Transaction();

        TransactionDetail detail = new TransactionDetail();
        detail.setSubTotal(200);
        List<TransactionDetail> details = new ArrayList<>();
        details.add(detail);

        transaction.setTransactionDetails(details);
        transaction.setGrandTotal(20000);
        repository.save(transaction);

        Transaction target = service.removeProduct(transaction, detail.getId());
        assertEquals(0, target.getTransactionDetails().size());
    }

    @Test
    void commitTransaction_shouldChangeTransactionStatusToPurchased_whenInvoked() {
        List<TransactionDetail> details = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setTransactionDetails(details);
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction);

        Product product = new Product();
        product.setName("Mango");
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

        Transaction commit = service.commitTransaction(transaction.getId());
        assertTrue(commit.getStatusEnum() == TransactionStatusEnum.PURCHASED);
    }

    @Test
    void getTransaction_shouldGetSpecificTransactionId() {
        Transaction transaction = new Transaction();
        repository.save(transaction);

        Transaction find = service.getTransaction(transaction.getId());
        assertSame(find.getId(), transaction.getId());
    }

    @Test
    void getTransaction_shouldThrowException_whenIdNotFound(){
        Transaction transaction = new Transaction();
        repository.save(transaction);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getTransaction("cow");
        });
    }

    @Test
    void getTransactionsByStatus_shouldGetTwoTransactionsByTheirStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setStatusEnum(TransactionStatusEnum.CART);
        repository.save(transaction1);

        Transaction find = new Transaction();
        find.setStatusEnum(TransactionStatusEnum.CART);

        Pageable pageable = PageRequest.of(0,2);
        Page<Transaction> transactions = service.getTransactionsByStatus(find, pageable);

        assertEquals(2, transactions.getTotalElements());
    }

    @Test
    void getTransactionsByAccountId_shouldGetListOfTransactionsByAccountID() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("cuculain");
        account.setEmail("cuculain");
        account.setPassword("cuculain");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setAccount(account);
        repository.save(transaction1);

        Page<Transaction> list = service.getTransactionsByAccountId(account.getId(), PageRequest.of(0,2));
        assertEquals(2, list.getTotalElements());

    }

    @Test
    void getCart_shouldGetCartOfAnAccountByAccountID() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("JoHnDoE");
        account.setEmail("johndoe@mail.co.ru");
        account.setPassword("johnDoe");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setAccount(account);
        repository.save(transaction);
        Transaction find = service.getCart(account.getId());
        assertNotNull(find);
    }

    @Test
    void setVoucher_shouldAbleToReduceGrandTotalInTransaction() {
        List<TransactionDetail> details = new ArrayList<>();

        Voucher voucher = new Voucher();
        voucher.setName("SKE48");
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

        Transaction finalTrans = repository.findById(transaction1.getId()).get();

        transaction1 = service.setVoucher(transaction1.getId(), transaction1);
        System.out.println(transaction.getId());
        assertEquals(10000, (int) transaction1.getGrandTotal());
    }

    @Test
    void setVoucher_shouldThrowException_whenVoucherExpired(){
        Voucher voucher = new Voucher();
        voucher.setName("Voucher");
        voucher.setValue(0.5);
        voucher.setValid(new Timestamp(System.currentTimeMillis() - (144*60*60*1000)));
        voucherRepository.save(voucher);

        Transaction transaction = new Transaction();
        transaction.setGrandTotal(20000);
        transaction.setTrxDate(new Timestamp(System.currentTimeMillis()));
        transaction.setVoucherName(voucher.getName());
        repository.save(transaction);

        assertThrows(VoucherInvalidException.class, () -> {
           service.setVoucher(transaction.getId(), transaction);
        });
    }

    @Test
    void getCartsOfAnAccount_shouldReturnCartOfAnAccount() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("Thor");
        account.setEmail("thor@mail.com");
        account.setPassword("johnThor");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setAccount(account);
        repository.save(transaction);

        Page<Transaction> transactions = service.getCartsOfAnAccount(account.getId(), PageRequest.of(0,1));
        assertEquals(1, transactions.getTotalElements());
    }

    @Test
    void getTransactionHistoriesOfAnAccount_shouldReturnTwoHistories_whenInvoked() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("falcon");
        account.setEmail("falcon@mail.com");
        account.setPassword("falcon");
        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.PURCHASED);
        transaction.setAccount(account);
        repository.save(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setStatusEnum(TransactionStatusEnum.PURCHASED);
        transaction1.setAccount(account);
        repository.save(transaction1);

        Page<Transaction> transactions = service.getTransactionHistoriesOfAnAccount(account.getId(), PageRequest.of(0,2));
        assertEquals(2, transactions.getTotalElements());
    }

    @Test
    void updateTransaction_shouldUpdateTransaction_whenInvoked() {

        Transaction transaction = new Transaction();
        transaction.setStatusEnum(TransactionStatusEnum.CART);
        transaction.setTransactionDetails(new ArrayList<>());
        repository.save(transaction);

        Transaction target = repository.findById(transaction.getId()).get();
        target.setTrxDate(new Timestamp(System.currentTimeMillis()));
        service.updateTransaction(target);

        assertTrue(transaction!=target);
    }
}