package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.AccountClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountClosingRepository extends JpaRepository<AccountClosingEntity, Long> {

    boolean existsByOrderId(Long orderId);

    Optional<AccountClosingEntity> findByOrderId(Long orderId);
}
