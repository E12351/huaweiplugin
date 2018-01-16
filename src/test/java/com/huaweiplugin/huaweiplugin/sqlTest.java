package com.huaweiplugin.huaweiplugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class sqlTest {

    @Autowired
    CustomerRepository repository;

    @Before
    public void init(){
        repository.save(new authData("Jack", "Smith"));
        repository.save(new authData("Adam", "Johnson"));
        repository.save(new authData("Kim", "Smith"));
        repository.save(new authData("David", "Williams"));
        repository.save(new authData("Peter", "Davis"));
    }

    @Test
    public void saveTest(){
        System.out.println(String.valueOf(repository.findByLastName("Johnson")));
        System.out.println(repository.findOne((long) 3).toString());

    }

    @After
    public void clean(){
        repository.deleteAll();
    }
}
