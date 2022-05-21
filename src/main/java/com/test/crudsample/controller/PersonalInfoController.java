package com.test.crudsample.controller;

import com.test.crudsample.model.PersonalInfo;
import com.test.crudsample.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/personal")
public class PersonalInfoController {
    @Autowired
    private PersonalInfoRepository personalInfoRepository;


    // get all personal infos
    @GetMapping("/")
    public ResponseEntity getAllPersonalInfo() {
        return ResponseEntity.ok().body(this.personalInfoRepository.findAll());
    }


    // get personal info by customer id
    @GetMapping("/{id}")
    public PersonalInfo getPersonalInfoByCustomerId (@PathVariable(value = "id")Long customerId) {
        return this.personalInfoRepository.getById(customerId);
    }


    //create personal info
    @PostMapping("/")
    public ResponseEntity createPersonalInfo (@RequestBody PersonalInfo personalInfo) {
        return ResponseEntity.ok().body(this.personalInfoRepository.save(personalInfo));
    }


    //delete personal info
    @DeleteMapping("/{id}")
    public ResponseEntity deletePersonalInfoById(@PathVariable(value = "id") Long customerId) {
        this.personalInfoRepository.deleteById(customerId);
        return ResponseEntity.ok().body("done");
    }


    // edit personal info by customer id
    @PutMapping("/{id}")
    public ResponseEntity editPersonalInfo(@PathVariable(value = "id")Long customerId, @RequestBody PersonalInfo newPersonalInfo) {
        PersonalInfo personalInfo = this.personalInfoRepository.getById(customerId);
        personalInfo.setAddress(newPersonalInfo.getAddress());
        personalInfo.setCustomer(newPersonalInfo.getCustomer());
        personalInfo.setEmail(newPersonalInfo.getEmail());
        personalInfo.setFirstName(newPersonalInfo.getFirstName());
        personalInfo.setLastName(newPersonalInfo.getLastName());

        return ResponseEntity.ok().body(this.personalInfoRepository.save(personalInfo));
    }
}
