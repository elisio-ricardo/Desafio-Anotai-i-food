package com.ricardo.desafioanotaai.controllers;


import com.ricardo.desafioanotaai.domain.product.Product;
import com.ricardo.desafioanotaai.domain.product.ProductDTO;
import com.ricardo.desafioanotaai.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Product")
public class ProductController {


    private ProductService ProductService;

    public ProductController(ProductService ProductService) {
        this.ProductService = ProductService;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO ProductDTO) {
        Product newProduct = this.ProductService.insert(ProductDTO);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> categories = this.ProductService.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO ProductDTO) {
        Product updateProduct = this.ProductService.update(id, ProductDTO);
        return ResponseEntity.ok().body(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") String id) {
        this.ProductService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
