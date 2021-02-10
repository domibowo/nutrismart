package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
 public Page<Product> findAllByCategory(Category category, Pageable pageable);
}
