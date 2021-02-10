package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.*;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.enigma.nutrismartbe.exception.InactiveAccountException;
import com.enigma.nutrismartbe.exception.NotEnoughException;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.exception.VoucherInvalidException;
import com.enigma.nutrismartbe.repository.TransactionRepository;
import com.enigma.nutrismartbe.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    TransactionDetailServiceImpl transactionDetailService;

    @Autowired
    VoucherServiceImpl voucherService;

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        Integer total = 0;
        Account validate = accountService.getAccount(transaction.getAccountId());
        if(validate.getIsActive()== ActiveStatusEnum.INACTIVE) throw new InactiveAccountException();
        Transaction trans = repository.findByStatusEnumAndAccount_Id(TransactionStatusEnum.CART, transaction.getAccountId());
        if(trans==null) {

            Transaction transaction1 = new Transaction();
            Account account = accountService.getAccount(transaction.getAccountId());
            List<TransactionDetail> details = new ArrayList<>();
            transaction1.setTransactionDetails(details);
            transaction1 = repository.save(transaction1);

            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(transaction1);
            Product product = productService.getProduct(transaction.getProductId());
            detail.setProduct(product);
            detail.setPrice(product.getPrice());
            detail.setSubTotal(detail.getPrice()*transaction.getProductQty());
            detail.setQuantity(transaction.getProductQty());

            List<TransactionDetail> trxDetail = transaction1.getTransactionDetails();
            trxDetail.add(detail);

            transaction1.setAccount(account);
            transaction1.setTrxDate(new Timestamp((System.currentTimeMillis())));
            transaction1.setGrandTotal(detail.getSubTotal());
            transaction1.setStatusEnum(TransactionStatusEnum.CART);
            return repository.save(transaction1);
        } else {
            Product product = productService.getProduct(transaction.getProductId());
            TransactionDetail detail = new TransactionDetail();
            detail.setProduct(product);
            detail.setPrice(product.getPrice());
            detail.setSubTotal(detail.getPrice()*transaction.getProductQty());
            detail.setQuantity(transaction.getProductQty());
            detail.setTransaction(trans);
            List<TransactionDetail> trxDetails = trans.getTransactionDetails();
            trxDetails.add(detail);
            total = trans.getGrandTotal();
            total = total + detail.getSubTotal();
            trans.setGrandTotal(total);
            trans.setTrxDate(new Timestamp(System.currentTimeMillis()));
            return repository.save(trans);
        }
    }

    @Override
    public Transaction removeProduct(Transaction transaction, String id) {
        Transaction transaction1 = repository.findById(transaction.getId()).get();
        List<TransactionDetail> details = transaction1.getTransactionDetails();
        TransactionDetail detail = transactionDetailService.getSpecificDetail(id);
        transaction1.setGrandTotal(transaction1.getGrandTotal()-detail.getSubTotal());
        details.remove(detail);
        transaction1.setTransactionDetails(details);
        return repository.save(transaction1);
    }

    @Override
    public Transaction commitTransaction(String id) {
        Transaction transaction = repository.findById(id).get();
        List<TransactionDetail> details = transaction.getTransactionDetails();
        for(TransactionDetail detail: details){
            Product product = detail.getProduct();
            Integer remaining = product.getStock()-detail.getQuantity();
            if(remaining<0){
                throw new NotEnoughException();
            } else {
                product.setStock(product.getStock()-detail.getQuantity());
            }
        }
        transaction.setStatusEnum(TransactionStatusEnum.PURCHASED);
        return repository.save(transaction);
    }

    @Override
    public Transaction getTransaction(String id) {
        Transaction transaction;
        if(repository.findById(id).isPresent()){
            transaction = repository.findById(id).get();
        } else throw new ResourceNotFoundException(id, Transaction.class);
        return transaction;
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        Integer total = transaction.getGrandTotal();
        List<TransactionDetail> details = transaction.getTransactionDetails();
        for(TransactionDetail detail: details){
            Product product = detail.getProduct();
            detail.setProduct(product);
            detail.setSubTotal(product.getPrice()*detail.getQuantity());
            detail.setTransaction(transaction);
            total = total + detail.getSubTotal();
        }
        transaction.setTransactionDetails(details);
        transaction.setGrandTotal(total);
        return repository.save(transaction);
    }

    @Override
    public Page<Transaction> getTransactionsByStatus(Transaction transaction, Pageable pageable) {
        Page<Transaction> transactions = repository.findAllByStatusEnum(transaction.getStatusEnum(), pageable);
        return transactions;
    }

    @Override
    public Page<Transaction> getTransactionsByAccountId(String id, Pageable pageable) {
        Account account = accountService.getAccount(id);
        Page<Transaction> transactions = repository.findAllTransactionsByAccount_Id(account.getId(), pageable);
        return transactions;
    }

    @Override
    public Transaction getCart(String id) {
        Account account = accountService.getAccount(id);
        Transaction transaction = repository.findByStatusEnumAndAccount_Id(TransactionStatusEnum.CART, account.getId());
        return transaction;
    }

    @Override
    public Transaction setVoucher(String id, Transaction transaction) {
        Transaction transaction1 = repository.findById(id).get();
        Voucher voucher = voucherService.getVoucher(transaction.getVoucherName());
        if(transaction1.getTrxDate().before(voucher.getValid())){
            List<TransactionDetail> details = transaction1.getTransactionDetails();
            Integer grandTotal = 0;
            for(TransactionDetail detail: details){
                grandTotal+=detail.getSubTotal();
            }
            transaction1.setGrandTotal((int) (grandTotal-(grandTotal*voucher.getValue())));
            transaction1.setVoucher(voucher);
        } else throw new VoucherInvalidException();
        return repository.save(transaction1);
    }

    @Override
    public Page<Transaction> getCartsOfAnAccount(String id, Pageable pageable) {
        Account account = accountService.getAccount(id);
        Page<Transaction> carts = repository.findAllByStatusEnumAndAccount_Id(TransactionStatusEnum.CART, account.getId(), pageable);
        return carts;
    }

    @Override
    public Page<Transaction> getTransactionHistoriesOfAnAccount(String id, Pageable pageable) {
        Account account = accountService.getAccount(id);
        Page<Transaction> histories = repository.findAllByStatusEnumAndAccount_Id(TransactionStatusEnum.PURCHASED, account.getId(), pageable);
        return histories;
    }
}
