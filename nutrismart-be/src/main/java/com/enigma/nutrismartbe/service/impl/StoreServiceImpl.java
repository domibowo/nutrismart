package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.entity.Store;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.StoreRepository;
import com.enigma.nutrismartbe.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    ProductServiceImpl productService;

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        Store updateStore = getStore(store.getId());
        List<Product> inputProduct = new ArrayList<Product>();
        List<Product> products = store.getProducts();
        for(Product product: products){
            Product input = productService.getProduct(product.getId());
            inputProduct.add(input);
        }
        updateStore.setProducts(inputProduct);
        return storeRepository.save(updateStore);
    }

    @Override
    public Store getStore(String id) {
        Store store;
        if(storeRepository.findById(id).isPresent()){
            store = storeRepository.findById(id).get();
        } else throw new ResourceNotFoundException(id, Store.class);
        return store;
    }

    @Override
    public Page<Store> getStores(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores;
    }

    @Override
    public void deleteStore(String id) {
        storeRepository.deleteById(id);
    }
}
