package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    public Transaction saveTransaction(Transaction transaction);
    public Transaction removeProduct(Transaction transaction, String id);
    public Transaction commitTransaction(String id);
    public Transaction getTransaction(String id);
    public Transaction updateTransaction(Transaction transaction);
    public Page<Transaction> getTransactionsByStatus(Transaction transaction, Pageable pageable);
    public Page<Transaction> getTransactionsByAccountId(String id, Pageable pageable);
    public Transaction getCart(String id);
    public Transaction setVoucher(String id, Transaction transaction);
    public Page<Transaction> getCartsOfAnAccount(String id, Pageable pageable);
    public Page<Transaction> getTransactionHistoriesOfAnAccount(String id, Pageable pageable);
}
