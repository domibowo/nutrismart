package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.enums.CategoryEnum;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.CategoryRepository;
import com.enigma.nutrismartbe.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanUp(){
        categoryRepository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        categoryRepository.deleteAll();
//    }

    @Test
    void saveCategory_shouldSaveOneCategory_whenInvoked() {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        categoryService.saveCategory(category);
        assertTrue(categoryRepository.findAll().size()==1);
    }

    @Test
    void getCategory_shouldGetSpecificCategory_whenInvoked() {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        categoryRepository.save(category);
        Category find = categoryService.getCategory(category.getId());
        assertEquals(category, find);
    }

    @Test
    void getCategory_shouldThrowException_whenIDNotFound(){
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        categoryRepository.save(category);
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategory("pig");
        });
    }

    @Test
    void deleteCategory_shouldDeleteSpecificCategory_whenInvoked() {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        categoryRepository.save(category);

        categoryService.deleteCategory(category.getId());
        assertFalse(categoryRepository.findById(category.getId()).isPresent());
    }

    @Test
    void getAllCategory_shouldGet2Category_whenInvoked() {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        categoryRepository.save(category);

        Category category1 = new Category();
        category1.setCategoryName(CategoryEnum.FRUITS);
        categoryRepository.save(category1);

        Page<Category> categories = categoryService.getAllCategory(PageRequest.of(0,2));
        assertTrue(categories.getTotalElements()==2);
    }
}