package io.github.williamandradesantana.restaurant.domain.entity;

import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private TableEntity table;

    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate;

    @Column(name = "closing_date", nullable = false)
    private LocalDateTime closingDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.OPEN;

    private String observation;

    @PrePersist
    public void prePersist() {
        openingDate = LocalDateTime.now();
    }

    public OrderEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
