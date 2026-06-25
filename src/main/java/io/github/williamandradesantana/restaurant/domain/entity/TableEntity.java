package io.github.williamandradesantana.restaurant.domain.entity;

import io.github.williamandradesantana.restaurant.domain.enums.TableStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_tables")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private String description;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private TableStatus status = TableStatus.FREE;

    public TableEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
