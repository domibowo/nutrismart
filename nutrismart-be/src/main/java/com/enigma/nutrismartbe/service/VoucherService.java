package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherService {

    public Voucher saveVoucher(Voucher voucher);
    public Voucher updateVoucher(Voucher voucher);
    public Voucher getVoucher(String name);
    public Page<Voucher> getAllVouchers(Pageable pageable);
    public void deleteVoucher(String id);
}
