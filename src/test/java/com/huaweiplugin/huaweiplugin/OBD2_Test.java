package com.huaweiplugin.huaweiplugin;


import com.huaweiplugin.Entity.deviceData;
import com.huaweiplugin.Repository.RepositoryDeviceData;
import com.huaweiplugin.Parameter.Constant;
import com.huaweiplugin.request.DataCollection;
import com.huaweiplugin.request.DeviceManagement;
import com.huaweiplugin.request.requestAuth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OBD2_Test {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private requestAuth requestauth;

    @Autowired
    private DeviceManagement devicemanagement;

    @Autowired
    private DataCollection datacollection;

    @Autowired
    RepositoryDeviceData repository;

    public static String accesstoken;
    public static String refreshtoken;

    @Before
    public void setup() throws Exception {
        HashMap data = requestauth.login("211.25.75.100","8743","L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga");
        assert data.get("state") != "HTTP/1.1 200 OK" : data.get("state");
        String accessToken = String.valueOf(data.get("accessToken"));
        String refreshToken = String.valueOf(data.get("refreshToken"));
        HuaweipluginApplicationTests.accesstoken = accessToken;
        HuaweipluginApplicationTests.refreshtoken = refreshToken;

        datacollection.subcribe(Constant.DEVICE_INFO_CHANGED);
        datacollection.subcribe(Constant.DEVICE_DATA_CHANGED);
        datacollection.subcribe(Constant.DEVICE_ADDED);

    }

    @Test
    public void obdTest() throws Exception {
        String SN1 = "36477713018504163780";
        String SN2 = "23803121497080952932";
        String SN3 = "61519419362392885558";

        HashMap responce1 = devicemanagement.regDirectDevice(SN1);
        log.info("SN : " + SN1 + "State : " + responce1.get("state") );
        repository.save(new deviceData(SN1,responce1.get("deviceId").toString(),responce1.get("verifyCode").toString(),responce1.get("psk").toString()));

        datacollection.deviceServiceInvocation("Discovery", responce1.get("deviceId").toString());

        HashMap responce2 = devicemanagement.regDirectDevice(SN2);
        log.info("SN : " + SN2 + "State : " + responce2.get("state") );
        repository.save(new deviceData(SN2,responce2.get("deviceId").toString(),responce2.get("verifyCode").toString(),responce2.get("psk").toString()));

        HashMap responce3 = devicemanagement.regDirectDevice(SN3);
        log.info("SN : " + SN3 + "State : " + responce3.get("state") );
        repository.save(new deviceData(SN3,responce3.get("deviceId").toString(),responce3.get("verifyCode").toString(),responce3.get("psk").toString()));


    }
}
