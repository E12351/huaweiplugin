package com.huaweiplugin.Repository;

import com.huaweiplugin.huaweiplugin.deviceData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositoryDeviceData extends CrudRepository<deviceData, String>{

    void delete(deviceData deleted);

    List<deviceData> findAll();

    deviceData findOne(String id);

    deviceData save(deviceData persisted);

}

