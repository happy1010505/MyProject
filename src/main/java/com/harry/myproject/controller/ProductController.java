package com.harry.myproject.controller;

import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;
import com.harry.myproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> search(@PathVariable Integer productId) {
        Product product = productService.getProduct(productId);
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
