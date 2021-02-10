package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.TransactionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionDetailService {

    public TransactionDetail saveDetail(TransactionDetail transactionDetail);
    public Page<TransactionDetail> getAllDetails(Pageable pageable);
    public void deleteDetail(String id);
    public TransactionDetail getSpecificDetail(String id);
}
