package com.test.crudsample.controller;

import com.test.crudsample.exception.ResourceNotFoundException;
import com.test.crudsample.model.Order;
import com.test.crudsample.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    // get all orders
    @GetMapping("/")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok().body(this.orderRepository.findAll());
    }

    // get order by id
    @GetMapping("/{id}")
    public ResponseEntity getOrderById (@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("no order with id " + orderId + " was found"));
        return ResponseEntity.ok().body(order);
    }

    // get orders by customer id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity getOrderByCustomerId (@PathVariable(value = "customerId") Long customerId) {
        List<Order> orderList = this.orderRepository.findAllByCustomerId(customerId);
        return ResponseEntity.ok().body(orderList);
    }

    // create new order
    @PostMapping("/")
    public ResponseEntity createOrder(@RequestBody Order orderInfo) {
        return ResponseEntity.ok().body(this.orderRepository.save(orderInfo));
    }

    // update order by id
    @PutMapping("/{id}")
    public ResponseEntity updateOrderById(@PathVariable(value = "id") Long orderId, @RequestBody Order orderInfo) throws ResourceNotFoundException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("no order with id " + orderId + " was found"));

        order.setUpdatedAt(orderInfo.getUpdatedAt());
        order.setCustomer(orderInfo.getCustomer());
        order.setStatus(orderInfo.getStatus());
        order.setTotalCost(orderInfo.getTotalCost());
        order.setCreatedAt(orderInfo.getCreatedAt());

        return ResponseEntity.ok().body(orderRepository.save(order));
    }

    // delete order by id
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderById (@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("no order with id " + orderId + " was found"));
        this.orderRepository.delete(order);

        return ResponseEntity.ok().body("true");
    }


}
