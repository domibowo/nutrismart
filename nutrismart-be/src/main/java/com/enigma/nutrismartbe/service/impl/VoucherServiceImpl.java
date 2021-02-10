package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Voucher;
import com.enigma.nutrismartbe.repository.VoucherRepository;
import com.enigma.nutrismartbe.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository repository;

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        voucher.setValid(new Timestamp(System.currentTimeMillis() + (144*60*60*1000)));
        return repository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        voucher.setValid(voucher.getValid());
        return repository.save(voucher);
    }

    @Override
    public Voucher getVoucher(String name) {
        Voucher find = repository.findByName(name);
        return find;
    }

    @Override
    public Page<Voucher> getAllVouchers(Pageable pageable) {
        Page<Voucher> vouchers = repository.findAll(pageable);
        return vouchers;
    }

    @Override
    public void deleteVoucher(String id) {
        repository.deleteById(id);
    }
}
