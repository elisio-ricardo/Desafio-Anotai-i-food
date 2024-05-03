package com.ricardo.desafioanotaai.service;


import com.ricardo.desafioanotaai.domain.category.Category;
import com.ricardo.desafioanotaai.domain.category.Exception.CategoryNotFoundException;
import com.ricardo.desafioanotaai.domain.product.Exception.ProductNotFoundException;
import com.ricardo.desafioanotaai.domain.product.Product;
import com.ricardo.desafioanotaai.domain.product.ProductDTO;
import com.ricardo.desafioanotaai.repository.ProductRepository;
import com.ricardo.desafioanotaai.service.aws.AwsSnsService;
import com.ricardo.desafioanotaai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    private final AwsSnsService awsSnsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService awsSnsService) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.awsSnsService = awsSnsService;
    }


    public Product insert(ProductDTO productDTO) {
        this.categoryService.getById(productDTO
                .categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productDTO);

        this.productRepository.save(newProduct);

        this.awsSnsService.publish(new MessageDTO(newProduct.toString()));

        return newProduct;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }


    public Product update(String id, ProductDTO productDTO) {
        Product updateProduct = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (productDTO.categoryId() != null) {
            this.categoryService.getById(productDTO.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            updateProduct.setCategory(productDTO.categoryId());
        }

        if (!productDTO.title().isEmpty()) updateProduct.setTitle(productDTO.title());
        if (!productDTO.description().isEmpty()) updateProduct.setDescription(productDTO.description());
        if (!(productDTO.price() == null)) updateProduct.setPrice(productDTO.price());

        this.productRepository.save(updateProduct);

        this.awsSnsService.publish(new MessageDTO(updateProduct.toString()));

        return updateProduct;

    }

    public void delete(String id) {
        Product Product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(Product);
    }
}
