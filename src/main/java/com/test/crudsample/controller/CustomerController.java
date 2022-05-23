package com.test.crudsample.controller;


import com.test.crudsample.exception.ResourceNotFoundException;
import com.test.crudsample.model.Customer;
import com.test.crudsample.model.Order;
import com.test.crudsample.repository.CustomerRepository;
import com.test.crudsample.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;


    // get all customers
    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    // get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> geCustomerById(@PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Customer with id " + customerId + " not found"));
        return ResponseEntity.ok().body(customer);
    }

    // add new customer
    @PostMapping("/")
    public Customer createCustomer(@RequestBody Customer customer) {
        List<Long> orderIdList = customer.getOrderList().stream().map(Order::getId).collect(Collectors.toList());

        List<Order> orderList = orderRepository.findAllById(orderIdList);
        customer.setOrderList(orderList);
        Customer persistedCustomer = this.customerRepository.save(customer);

        orderList.forEach(order -> {
            order.setCustomer(customer);
        });
        orderRepository.saveAll(orderList);

        return persistedCustomer;
    }

    // update customer by id
    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable(value = "id") Long customerId, @RequestBody Customer customerInfo)
            throws ResourceNotFoundException {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Customer with id " + customerId + " not found"));

        customer.setFirstName(customerInfo.getFirstName());
        customer.setLastName(customerInfo.getLastName());
        customer.setEmail(customerInfo.getEmail());

        return ResponseEntity.ok().body(this.customerRepository.save(customer));
    }

    // delete customer by id
    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Customer with id " + customerId + " not found"));

        this.customerRepository.delete(customer);

        return true;
    }
}
