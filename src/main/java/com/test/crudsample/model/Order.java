package com.test.crudsample.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.crudsample.resource.enums;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Order {

    public Order() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Column(name = "total_cost")
    private Long totalCost;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "status")
    private enums.ORDER_STATUS status;
    
//    @ManyToOne
//    @JoinColumn(name = "basket_id")
//    @JsonBackReference
//    private Basket basket;

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public enums.ORDER_STATUS getStatus() {
        return status;
    }

    public void setStatus(enums.ORDER_STATUS status) {
        this.status = status;
    }

//    public Basket getBasket() {
//        return basket;
//    }
//
//    public void setBasket(Basket basket) {
//        this.basket = basket;
//    }
}
