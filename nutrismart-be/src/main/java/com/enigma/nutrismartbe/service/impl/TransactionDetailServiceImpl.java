package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.Transaction;
import com.enigma.nutrismartbe.entity.TransactionDetail;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.enigma.nutrismartbe.repository.TransactionDetailRepository;
import com.enigma.nutrismartbe.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    TransactionDetailRepository repository;

    @Autowired
    TransactionServiceImpl transactionService;

    @Override
    public TransactionDetail saveDetail(TransactionDetail transactionDetail) {
        return repository.save(transactionDetail);
    }

    @Override
    public Page<TransactionDetail> getAllDetails(Pageable pageable) {
        Page<TransactionDetail> details = repository.findAll(pageable);
        return details;
    }

    @Override
    public void deleteDetail(String id) {
        repository.deleteById(id);
    }

    @Override
    public TransactionDetail getSpecificDetail(String id) {
        TransactionDetail detail = repository.findById(id).get();
        return detail;
    }
}
