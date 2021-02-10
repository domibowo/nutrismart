package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.CategoryRepository;
import com.enigma.nutrismartbe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(String id) {
        Category category;
        if (categoryRepository.findById(id).isPresent()){
           category = categoryRepository.findById(id).get();
        }else throw new ResourceNotFoundException(id, Category.class);
        return category;
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getAllCategory(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }
}
