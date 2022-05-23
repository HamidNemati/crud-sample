package com.test.crudsample.controller;

import com.test.crudsample.exception.ResourceNotFoundException;
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
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerController customerController;

    private Customer persistedCustomer;


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(customerRepository);
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(14, customerRepository.findAll().size());
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
        Customer fetched = customerRepository.findAll().stream().filter(c -> c.getId() == persistedCustomer.getId()).collect(Collectors.toList()).get(0);

        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(persistedCustomer.getId(), fetched.getId());
        Assertions.assertEquals(persistedCustomer.getFirstName(), fetched.getFirstName());
        Assertions.assertEquals(persistedCustomer.getLastName(), fetched.getLastName());
        Assertions.assertEquals(persistedCustomer.getEmail(), fetched.getEmail());
    }

    @Test
    public void updateCustomerTest() {
        Customer customerInfo = new Customer();
        customerInfo.setLastName("edited");
        customerInfo.setLastName("edited");
        customerInfo.setEmail("edited");

        try {
            customerController.updateCustomer(persistedCustomer.getId(), customerInfo);

            Customer fetched = customerRepository.findAll().stream().filter(c -> c.getId() == persistedCustomer.getId()).collect(Collectors.toList()).get(0);

            Assertions.assertNotNull(fetched);

            Assertions.assertEquals(customerInfo.getFirstName(), fetched.getFirstName());
            Assertions.assertEquals(customerInfo.getLastName(), fetched.getLastName());
            Assertions.assertEquals(customerInfo.getEmail(), fetched.getEmail());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

}
