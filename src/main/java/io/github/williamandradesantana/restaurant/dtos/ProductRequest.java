package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.ProductCategoryEntity;
import io.github.williamandradesantana.restaurant.domain.entity.ProductEntity;

import java.math.BigDecimal;

public record ProductRequest(
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        Integer preparationTimeInMinutes
) {
    public ProductEntity toEntity(ProductCategoryEntity category) {
        ProductEntity product = new ProductEntity();
        insert(product, category);
        return product;
    }

    public void insert(ProductEntity product, ProductCategoryEntity category) {
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailable(available != null ? available : true);
        product.setPreparationTimeInMinutes(preparationTimeInMinutes);
    }
}
