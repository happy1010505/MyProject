package com.harry.myproject.controller;

import com.harry.myproject.Constant.ProductCategory;
import com.harry.myproject.dto.ProductQueryParam;
import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;
import com.harry.myproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam (required = false) ProductCategory productCategory,
                                                     @RequestParam (required = false) String search) {
        ProductQueryParam productQueryParam = new ProductQueryParam();
        productQueryParam.setProductCategory(productCategory);
        productQueryParam.setSearch(search);
        List<Product> products = productService.getProducts(productQueryParam);
        return ResponseEntity.ok().body(products);
    }

    // 查詢商品
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    // 新增商品
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    // 更新商品
    @PutMapping("/products/{productId}")
    public ResponseEntity<?> upDateProduct(@PathVariable Integer productId,
                                           @RequestBody @Valid ProductRequest productRequest){
        // 查看數據是否存在
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        // 更新數據
        productService.updateProduct(productId,productRequest);
        Product newProduct = productService.getProductById(productId);
        return ResponseEntity.ok(newProduct);

    }
    // 刪除商品
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
