package com.huaweiplugin.huaweiplugin;


import com.huaweiplugin.Repository.RepositoryDeviceData;
import com.huaweiplugin.services.requests;
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
    public requests request;

    @Autowired
    RepositoryDeviceData repository;

    public static String accesstoken;
    public static String refreshtoken;

    @Before
    public void setup() throws Exception {
        HashMap data = request.login("211.25.75.100","8743","L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga");
        assert data.get("state") != "HTTP/1.1 200 OK" : data.get("state");
        String accessToken = String.valueOf(data.get("accessToken"));
        String refreshToken = String.valueOf(data.get("refreshToken"));
        HuaweipluginApplicationTests.accesstoken = accessToken;
        HuaweipluginApplicationTests.refreshtoken = refreshToken;

        request.subcribe("deviceInfoChanged");
        request.subcribe("deviceDataChanged");
        request.subcribe("deviceAdded");


    }

    @Test
    public void obdTest() throws Exception {
        String SN1 = "36477713018504163780";
        String SN2 = "23803121497080952932";
        String SN3 = "61519419362392885558";

        HashMap responce1 = request.regDirectDevice(SN1);
        log.info("SN : " + SN1 + "State : " + responce1.get("state") );
        repository.save(new deviceData(SN1,responce1.get("deviceId").toString(),responce1.get("verifyCode").toString(),responce1.get("psk").toString()));

        request.deviceServiceInvocation("Discovery", responce1.get("deviceId").toString());

        HashMap responce2 = request.regDirectDevice(SN2);
        log.info("SN : " + SN2 + "State : " + responce2.get("state") );
        repository.save(new deviceData(SN2,responce2.get("deviceId").toString(),responce2.get("verifyCode").toString(),responce2.get("psk").toString()));

        HashMap responce3 = request.regDirectDevice(SN3);
        log.info("SN : " + SN3 + "State : " + responce3.get("state") );
        repository.save(new deviceData(SN3,responce3.get("deviceId").toString(),responce3.get("verifyCode").toString(),responce3.get("psk").toString()));


    }
}
