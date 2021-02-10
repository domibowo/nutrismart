package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Voucher;
import com.enigma.nutrismartbe.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoucherController {

    @Autowired
    VoucherServiceImpl voucherService;

    @PostMapping("/admin/voucher/form")
    public Voucher saveVoucher(@RequestBody Voucher voucher){
        return voucherService.saveVoucher(voucher);
    }

    @PutMapping("/admin/voucher/form")
    public Voucher updateVoucher(@RequestBody Voucher voucher){
        return voucherService.saveVoucher(voucher);
    }

    @GetMapping("/voucher")
    public Page<Voucher> getAllActiveVouchers(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return voucherService.getAllVouchers(pageable);
    }
}
