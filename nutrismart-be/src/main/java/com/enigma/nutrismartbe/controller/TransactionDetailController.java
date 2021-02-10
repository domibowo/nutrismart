package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.TransactionDetail;
import com.enigma.nutrismartbe.service.impl.TransactionDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionDetailController {

    @Autowired
    TransactionDetailServiceImpl detailService;

    @PostMapping("/transaction-detail/form")
    public TransactionDetail saveDetail(@RequestBody TransactionDetail transactionDetail){
        return detailService.saveDetail(transactionDetail);
    }
}
