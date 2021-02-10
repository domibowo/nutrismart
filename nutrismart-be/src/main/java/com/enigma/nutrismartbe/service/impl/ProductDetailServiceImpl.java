package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.ProductDetail;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.ProductDetailRepository;
import com.enigma.nutrismartbe.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail getProductDetail(String id) {
        ProductDetail productDetail;
        if (productDetailRepository.findById(id).isPresent()) {
            productDetail = productDetailRepository.findById(id).get();
        }else throw new ResourceNotFoundException(id, ProductDetail.class);
        return productDetail;
    }

    @Override
    public void deleteProductDetail(String id) {
        productDetailRepository.deleteById(id);
    }

    @Override
    public Page<ProductDetail> getAllProductDetail(Pageable pageable) {
        Page<ProductDetail> productDetails = productDetailRepository.findAll(pageable);
        return productDetails;
    }
}
