package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailService {

    public ProductDetail saveProductDetail(ProductDetail productDetail);
    public ProductDetail getProductDetail(String id);
    public void deleteProductDetail(String id);
    public Page<ProductDetail> getAllProductDetail(Pageable pageable);
}
