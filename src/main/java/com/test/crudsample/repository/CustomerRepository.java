package com.test.crudsample.repository;

import com.test.crudsample.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

}
