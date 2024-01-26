package com.pedro.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_ORDER") // ORDER is not a valid name for table
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Status status;

    Order() {
    }

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(description, order.description)) return false;
        return Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.status);
    }

    @Override
    public String toString() {
        return String.format("Order{id=%s, description=%s, status=%s}", id, description, status);
    }
}
