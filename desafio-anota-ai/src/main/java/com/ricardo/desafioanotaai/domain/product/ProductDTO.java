package com.ricardo.desafioanotaai.domain.product;

import com.ricardo.desafioanotaai.domain.category.Category;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
