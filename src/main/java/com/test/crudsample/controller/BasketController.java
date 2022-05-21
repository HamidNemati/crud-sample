//package com.test.crudsample.controller;
//
//import com.test.crudsample.model.Basket;
//import com.test.crudsample.repository.BasketRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping(value = "api/v1/basket")
//public class BasketController {
//
//
//
//    @Autowired
//    private BasketRepository basketRepository;
//
//
//    // get all baskets
//    @GetMapping(value = "/")
//    public ResponseEntity getAllBaskets() {
//        return ResponseEntity.ok().body(this.basketRepository.findAll());
//    }
//
//
//    // create basket
//    @PostMapping(value = "/")
//    public ResponseEntity createBasket(@RequestBody Basket basketInfo) {
//        return ResponseEntity.ok().body(this.basketRepository.save(basketInfo));
//    }
//
//}
