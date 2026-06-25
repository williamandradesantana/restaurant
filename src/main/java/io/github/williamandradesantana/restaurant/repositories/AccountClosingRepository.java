package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.AccountClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountClosingRepository extends JpaRepository<AccountClosingEntity, Long> {
}
