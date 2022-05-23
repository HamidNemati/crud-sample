package com.test.crudsample.controller;

import com.test.crudsample.exception.ResourceNotFoundException;
import com.test.crudsample.model.Customer;
import com.test.crudsample.model.PersonalInfo;
import com.test.crudsample.repository.CustomerRepository;
import com.test.crudsample.repository.PersonalInfoRepository;
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
public class personalInfoControllerTest {
    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PersonalInfoController personalInfoController;

    private PersonalInfo persistedPersonalInfo;


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(personalInfoRepository);
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(2, personalInfoRepository.findAll().size());
    }

    @Before
    public void persistOnePersonalInfo () {
        PersonalInfo personalInfo1 = new PersonalInfo();
        personalInfo1.setFirstName("firstName");
        personalInfo1.setLastName("lastName");
        personalInfo1.setEmail("email");
        persistedPersonalInfo = personalInfoRepository.save(personalInfo1);
    }

    @After
    public void removePersistedPersonalInfo () {
        personalInfoRepository.deleteById(persistedPersonalInfo.getId());
    }



    @Test
    public void setPersistedPersonalInfo () {
        Assert.assertEquals(1, personalInfoRepository.findAllById(Collections.singleton(persistedPersonalInfo.getId())).size());

        long fakeId = 2000;
        Assert.assertEquals(0, personalInfoRepository.findAllById(Collections.singleton(fakeId)).size());
    }

    @Test
    public void getByIdTest () {
        PersonalInfo fetched = personalInfoRepository.findAll().stream().filter(c -> c.getId().equals(persistedPersonalInfo.getId())).collect(Collectors.toList()).get(0);

        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(persistedPersonalInfo.getId(), fetched.getId());
        Assertions.assertEquals(persistedPersonalInfo.getFirstName(), fetched.getFirstName());
        Assertions.assertEquals(persistedPersonalInfo.getLastName(), fetched.getLastName());
        Assertions.assertEquals(persistedPersonalInfo.getEmail(), fetched.getEmail());
    }

    @Test
    public void updatePersonalInfoTest() {
        PersonalInfo personalInfoInfo = new PersonalInfo();
        personalInfoInfo.setFirstName("edited");
        personalInfoInfo.setLastName("edited");
        personalInfoInfo.setEmail("edited");
        personalInfoInfo.setAddress("edited");
        personalInfoInfo.setCustomer(customerRepository.findAll().get(0));

        PersonalInfo updated = (PersonalInfo) personalInfoController.updatePersonalInfo(persistedPersonalInfo.getId(), personalInfoInfo).getBody();

        Assertions.assertNotNull(updated);

        Assertions.assertEquals(personalInfoInfo.getFirstName(), updated.getFirstName());
        Assertions.assertEquals(personalInfoInfo.getLastName(), updated.getLastName());
        Assertions.assertEquals(personalInfoInfo.getEmail(), updated.getEmail());
        Assertions.assertEquals(personalInfoInfo.getAddress(), updated.getAddress());

    }
}
