package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Repository.RepositoryAuthData;
import com.huaweiplugin.Repository.RepositoryDeviceData;
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
    RepositoryDeviceData repository;

    @Autowired
    RepositoryAuthData repositoryAuth;

    @Before
    public void init(){
        repositoryAuth.save(new authData(1,"Huawei", "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga"));
        repositoryAuth.save(new authData(2,"Huawei", "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga"));

    }

    @Test
    public void saveTest(){
        System.out.println(repositoryAuth.findOne((long) 1).toString());

        System.out.println(repositoryAuth.findAll());

    }

    @Test
    public void updateTest(){
        repositoryAuth.save(new authData(2,"Huawei", "test","update"));
        System.out.println(repositoryAuth.findOne((long) 2).toString());
        System.out.println(repositoryAuth.findAll());

    }

    @Test
    public void findTest(){
        System.out.println(repository.hashCode());

    }

    @Test
    public void deviceDataTest(){
        repository.save(new deviceData("mac","deviceId","verifyCode","psk"));
    }

    @After
    public void clean(){
//        repository.deleteAll();
    }
}
