package com.test.crudsample.controller;

import com.test.crudsample.model.Customer;
import com.test.crudsample.repository.CustomerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer persistedCustomer;


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(customerRepository);
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(17, customerRepository.findAll().size());
    }

    @Before
    public void persistOneCustomer () {
        Customer customer1 = new Customer();
        customer1.setFirstName("firstName");
        customer1.setLastName("lastName");
        customer1.setEmail("email");
        persistedCustomer = customerRepository.save(customer1);
    }

    @After
    public void removePersistedCustomer () {
        customerRepository.deleteById(persistedCustomer.getId());
    }



    @Test
    public void setPersistedCustomer () {
        Assert.assertEquals(1, customerRepository.findAllById(Collections.singleton(persistedCustomer.getId())).size());

        long fakeId = 2000;
        Assert.assertEquals(0, customerRepository.findAllById(Collections.singleton(fakeId)).size());
    }

    @Test
    public void getByIdTest () {
        Customer fetched = customerRepository.getById(persistedCustomer.getId());
        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(persistedCustomer, fetched);
    }
}
