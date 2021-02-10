package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    public Category getCategory(String id);
    public Category saveCategory(Category category);
    public Page<Category> getAllCategory(Pageable pageable);
    public void deleteCategory(String id);

}
