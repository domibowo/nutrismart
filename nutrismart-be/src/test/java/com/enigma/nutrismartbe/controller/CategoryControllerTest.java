package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Category;
import com.enigma.nutrismartbe.enums.CategoryEnum;
import com.enigma.nutrismartbe.repository.CategoryRepository;
import com.enigma.nutrismartbe.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryService service;

    @Autowired
    CategoryController controller;

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void getCategory_shouldReturnStatusOK_whenAbleToGetSpecificCategory() throws Exception {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ANIMAL_PRODUCTS);
        service.saveCategory(category);

        controller.getCategory(category.getId());
        RequestBuilder builder = MockMvcRequestBuilders.get("/category/"+category.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(category));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveCategory_shouldReturnStatusOK_whenSuccessfullySaveACategory() throws Exception {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.FRUITS);
        controller.saveCategory(category);

        RequestBuilder builder = MockMvcRequestBuilders
                .post("/admin/category").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(category));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateCategory_whenSuccessfullyUpdateCategory_shouldReturnStatusOK() throws Exception {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.ROOTS);
        service.saveCategory(category);

        category.setCategoryName(CategoryEnum.OTHERS);
        controller.updateCategory(category);

        RequestBuilder builder = MockMvcRequestBuilders.put("/admin/category")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(category));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllCategory_shouldReturnStatusOK_whenSuccessfullyGetAllCategory() throws Exception {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.VEGGIES);
        service.saveCategory(category);

        Category category1 = new Category();
        category1.setCategoryName(CategoryEnum.PROCESSED_PRODUCTS);
        service.saveCategory(category1);

        controller.getAllCategory(1, 2);

        RequestBuilder builder = MockMvcRequestBuilders.get("/category")
                .param("page", "1").param("size","2").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCategory_whenSuccessfullyDeleteACategory_shouldReturnStatusOK() throws Exception {
        Category category = new Category();
        category.setCategoryName(CategoryEnum.SEEDS);
        service.saveCategory(category);

        RequestBuilder builder = MockMvcRequestBuilders.delete("/admin/category/"+category.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(category));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}