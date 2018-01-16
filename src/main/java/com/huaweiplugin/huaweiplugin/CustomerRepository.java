package com.huaweiplugin.huaweiplugin;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//import com.javasampleapproach.mysql.model.Customer;

public interface CustomerRepository extends CrudRepository<authData, Long>{
    List<authData> findByLastName(String lastName);
}