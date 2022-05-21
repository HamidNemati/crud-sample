//package com.test.crudsample.model;
//
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import org.hibernate.annotations.Cascade;
//
//import javax.persistence.*;
//import java.sql.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "basket")
//public class Basket {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id;
//
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "customer_id")
//    @JsonBackReference
//    private Customer customer;
//
//
//    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Order> orderList;
//
//
//    @Column(name = "expiration_date")
//    private Date expirationDate;
//
//    public Basket() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public List<Order> getOrderList() {
//        return orderList;
//    }
//
//    public void setOrderList(List<Order> orderList) {
//        this.orderList = orderList;
//    }
//
//    public Date getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(Date expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//}
