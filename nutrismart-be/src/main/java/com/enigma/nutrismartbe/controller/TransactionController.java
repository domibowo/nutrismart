package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Transaction;
import com.enigma.nutrismartbe.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/account/transaction/{id}")
    public Transaction getTransaction(@PathVariable String id){
        return transactionService.getTransaction(id);
    }

    @PostMapping("/account/transaction/cart")
    public Transaction saveTransaction(@RequestBody Transaction transaction){
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/account/transaction/cart/voucher/{id}")
    public Transaction setVoucher(@PathVariable String id, @RequestBody Transaction transaction){
        return transactionService.setVoucher(id, transaction);
    }

    @PutMapping("/account/transaction/purchased/{id}")
    public Transaction commitTransaction(@PathVariable String id){
        return transactionService.commitTransaction(id);
    }

    @PutMapping("/account/transaction/cart/{id}")
    public Transaction removeProduct(@PathVariable String id, @RequestBody Transaction transaction){
        return transactionService.removeProduct(transaction, id);
    }

    @GetMapping("/account/transaction/status/{id}")
    public Transaction getCart(@PathVariable String id){
        return transactionService.getCart(id);
    }

    @GetMapping("/admin/transaction/{id}")
    public Page<Transaction> getTransactionsById(@PathVariable String id, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionService.getTransactionsByAccountId(id, pageable);
    }

    @GetMapping("/admin/transaction/status")
    public Page<Transaction> getTransactionsByStatus(@RequestBody Transaction transaction, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionService.getTransactionsByStatus(transaction, pageable);
    }

    @GetMapping("/account/transaction/cart/{id}")
    public Page<Transaction> getCartsofAnAccount(@PathVariable String id, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionService.getCartsOfAnAccount(id, pageable);
    }

    @GetMapping("/account/transaction/history/{id}")
    public Page<Transaction> getHistories(@PathVariable String id, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return transactionService.getTransactionHistoriesOfAnAccount(id, pageable);
    }

    @PutMapping("/account/transaction/update")
    public Transaction updateCart(@RequestBody Transaction transaction){
        return transactionService.updateTransaction(transaction);
    }
}
