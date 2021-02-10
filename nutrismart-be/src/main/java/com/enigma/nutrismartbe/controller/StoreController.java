package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Store;
import com.enigma.nutrismartbe.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {

    @Autowired
    StoreServiceImpl storeService;

    @PutMapping("/admin/store/form")
    public Store updateStore(@RequestBody Store store){
        return storeService.updateStore(store);
    }

    @PostMapping("/admin/store/form")
    public Store createBranch(@RequestBody Store store){
        return storeService.saveStore(store);
    }

    @GetMapping("/admin/store")
    public Page<Store> getAllStores(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return storeService.getStores(pageable);
    }

    @GetMapping("/admin/store/{id}")
    public Store getStore(@PathVariable String id){
        return storeService.getStore(id);
    }
}
