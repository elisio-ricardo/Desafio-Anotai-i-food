package com.ricardo.desafioanotaai.service;


import com.ricardo.desafioanotaai.domain.category.Category;
import com.ricardo.desafioanotaai.domain.category.Exception.CategoryNotFoundException;
import com.ricardo.desafioanotaai.domain.product.Exception.ProductNotFoundException;
import com.ricardo.desafioanotaai.domain.product.Product;
import com.ricardo.desafioanotaai.domain.product.ProductDTO;
import com.ricardo.desafioanotaai.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private CategoryService categoryService;
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product insert(ProductDTO productDTO) {
        Category category = categoryService.getById(productDTO
                .categoryId()).orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(productDTO);
        newProduct.setCategory(category);

        this.productRepository.save(newProduct);
        return newProduct;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }


    public Product update(String id, ProductDTO productDTO) {
        Product updateProduct = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (!productDTO.title().isEmpty()) updateProduct.setTitle(productDTO.title());
        if (!productDTO.description().isEmpty()) updateProduct.setDescription(productDTO.description());
        if (!(productDTO.price() == null)) updateProduct.setPrice(productDTO.price());

        categoryService.getById(productDTO.categoryId())
                .ifPresent(updateProduct::setCategory);

        this.productRepository.save(updateProduct);

        return updateProduct;

    }

    public void delete(String id) {
        Product Product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(Product);
    }
}
