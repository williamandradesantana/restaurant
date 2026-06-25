package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
}
