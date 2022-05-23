package com.test.crudsample.controller;

import com.test.crudsample.exception.ResourceNotFoundException;
import com.test.crudsample.model.Order;
import com.test.crudsample.repository.CustomerRepository;
import com.test.crudsample.repository.OrderRepository;
import com.test.crudsample.resource.enums;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderController orderController;

    @Autowired
    private CustomerRepository customerRepository;

    private Order persistedOrder;


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(orderRepository);
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(9, orderRepository.findAll().size());
    }

    @Before
    public void persistOneOrder () {
        Order order1 = new Order();
        order1.setStatus(enums.ORDER_STATUS.CREATED);
        order1.setCustomer(customerRepository.getById((long) 22));
        order1.setTotalCost((long) 10000);
        order1.setCreatedAt(new Timestamp((new Date()).getTime()));
        order1.setUpdatedAt(new Timestamp((new Date()).getTime()));
        persistedOrder = orderRepository.save(order1);
    }

    @After
    public void removePersistedOrder () {
        orderRepository.deleteById(persistedOrder.getId());
    }



    @Test
    public void setPersistedOrder () {
        Assert.assertEquals(1, orderRepository.findAllById(Collections.singleton(persistedOrder.getId())).size());

        long fakeId = 2000;
        Assert.assertEquals(0, orderRepository.findAllById(Collections.singleton(fakeId)).size());
    }

    @Test
    public void getByIdTest () {
        Order fetched = null;
        try {
            fetched = (Order) orderController.getOrderById(persistedOrder.getId()).getBody();
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(persistedOrder.getId(), fetched.getId());
        Assertions.assertEquals(persistedOrder.getStatus(), fetched.getStatus());
        Assertions.assertEquals(persistedOrder.getTotalCost(), fetched.getTotalCost());
        Assertions.assertEquals(persistedOrder.getCreatedAt(), fetched.getCreatedAt());
    }

    @Test
    public void updateOrderTest() {
        Order orderInfo = new Order();
        orderInfo.setStatus(enums.ORDER_STATUS.FINISHED);
        orderInfo.setTotalCost((long) 20000);
        orderInfo.setCreatedAt(new Timestamp((new Date()).getTime()));
        orderInfo.setUpdatedAt(new Timestamp((new Date()).getTime()));

        Order f = orderRepository.findAll().stream().filter(c -> c.getId().equals(persistedOrder.getId())).collect(Collectors.toList()).get(0);
        try {
            Order updated = (Order) orderController.updateOrderById(persistedOrder.getId(), orderInfo).getBody();

            Assertions.assertNotNull(updated);

            Assertions.assertEquals(orderInfo.getStatus(), updated.getStatus());
            Assertions.assertEquals(orderInfo.getTotalCost(), updated.getTotalCost());
            Assertions.assertEquals(orderInfo.getCreatedAt(), updated.getCreatedAt());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
