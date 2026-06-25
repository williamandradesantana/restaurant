package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
}
