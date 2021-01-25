package com.shopapp.products.controller;

import com.shopapp.products.model.Product;
import com.shopapp.products.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RepositoryRestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        Iterable<Product> products = productRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("message", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        Map<String, Object> response = new HashMap<>();
        response.put("product", savedProduct);
        response.put("message", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        Optional<Product> productExists = productRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        productExists.ifPresentOrElse((product) -> {
            response.put("product", product);
            response.put("message", "success");
            response.put("status", HttpStatus.OK);
        }, () -> {
            response.put("error", "no such product");
            response.put("message", "sorry");
            response.put("status", HttpStatus.NOT_FOUND);
        });

        return new ResponseEntity(response, (HttpStatus) response.get("status"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        log.info(id);
        Product existingProduct = productRepository.findById(Long.valueOf(id)).get();
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setListPrice(product.getListPrice());
        Map<String, Object> response = new HashMap<>();
        Product updatedProduct = productRepository.save(existingProduct);
        response.put("product", updatedProduct);
        response.put("message", "updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        Map<String, Object> response = new HashMap<>();
        productRepository.deleteById(id);
        response.put("message", "deleted successfully");
        return ResponseEntity.ok(response);
    }
}
