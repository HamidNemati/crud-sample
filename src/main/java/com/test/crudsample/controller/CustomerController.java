package com.test.crudsample.controller;


import com.test.crudsample.exception.ResourceNotFoundException;
import com.test.crudsample.model.Customer;
import com.test.crudsample.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;


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
        return this.customerRepository.save(customer);
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
