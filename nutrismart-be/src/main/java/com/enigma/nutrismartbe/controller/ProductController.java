package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Product;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.service.impl.ProductServiceImpl;
import com.enigma.nutrismartbe.util.FileUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;


@RestController
//@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FileUtility fileUtility;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable String id){
        return productService.getProduct(id);
    }

    @PostMapping("/admin/product/form")
    public Product saveProduct(@RequestPart MultipartFile file, @RequestPart String form) throws IOException {
        return productService.saveProductImage(file, form);
    }

    @PutMapping("/admin/product/form")
    public Product updateProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping("/product")
    public Page<Product> getAllProduct(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return productService.getAllProduct(pageable);
    }

    @GetMapping("/product/category/{id}")
    public Page<Product> getProductsByCategory(@PathVariable String id, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return productService.getProductsByCategory(id, pageable);
    }

    @DeleteMapping("/admin/product/{id}")
    public void deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
    }

    @GetMapping("/product/image/{id}")
    public ResponseEntity<Resource> getProductPhoto(@PathVariable String id, HttpServletRequest request) throws IOException {
        Product product = productService.getProduct(id);
        if(product==null) throw new ResourceNotFoundException(id, Product.class);
        Resource resource = fileUtility.read(product.getPhoto());
        String content = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(content))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + resource.getFilename() + "\"")
                .body(resource);
    }
}
