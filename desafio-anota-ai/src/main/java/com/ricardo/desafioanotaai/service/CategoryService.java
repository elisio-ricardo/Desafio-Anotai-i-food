package com.ricardo.desafioanotaai.service;


import com.ricardo.desafioanotaai.domain.category.Category;
import com.ricardo.desafioanotaai.domain.category.CategoryDTO;
import com.ricardo.desafioanotaai.domain.category.Exception.CategoryNotFoundException;
import com.ricardo.desafioanotaai.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category insert(CategoryDTO categoryDTO) {
        Category newCategory = new Category(categoryDTO);
        this.categoryRepository.save(newCategory);
        return newCategory;
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> getById(String id) {
        return this.categoryRepository.findById(id);
    }


    public Category update(String id, CategoryDTO categoryDTO) {
        Category updateCategory = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if (!categoryDTO.title().isEmpty()) updateCategory.setTitle(categoryDTO.title());
        if (!categoryDTO.description().isEmpty()) updateCategory.setDescriptions(categoryDTO.description());

        this.categoryRepository.save(updateCategory);

        return updateCategory;

    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.categoryRepository.delete(category);
    }
}
