package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/account/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable String id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/admin/category")
    public Category saveCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @PutMapping("/admin/category")
    public  Category updateCategory(@RequestBody Category category){
        return  categoryService.saveCategory(category);
    }

    @GetMapping("/category")
    public Page<Category> getAllCategory(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return categoryService.getAllCategory(pageable);
    }

    @DeleteMapping("/admin/category/{id}")
    public void deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
    }
}
