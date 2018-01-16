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
        repository.save(new authData(1,"Huawei", "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga"));
        repository.save(new authData(2,"Huawei", "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga"));
    }

    @Test
    public void saveTest(){
        System.out.println(repository.findOne((long) 1).toString());

        System.out.println(repository.findAll());

    }

    @Test
    public void updateTest(){
        repository.save(new authData(2,"Huawei", "test","update"));
        System.out.println(repository.findOne((long) 2).toString());
        System.out.println(repository.findAll());

    }

    @Test
    public void findTest(){
        System.out.println(repository.hashCode());

    }

    @After
    public void clean(){
//        repository.deleteAll();
    }
}
