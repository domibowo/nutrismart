package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {

    public Store saveStore(Store store);
    public Store updateStore(Store store);
    public Store getStore(String id);
    public Page<Store> getStores(Pageable pageable);
    public void deleteStore(String id);
}
