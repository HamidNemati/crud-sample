package com.test.crudsample.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@ToString
@Getter
@Setter
@NoArgsConstructor
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

}
