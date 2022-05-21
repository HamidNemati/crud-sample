package com.test.crudsample.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @JsonBackReference(value = "personalInfo")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "customer", targetEntity = Order.class)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JsonManagedReference
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public Customer() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
