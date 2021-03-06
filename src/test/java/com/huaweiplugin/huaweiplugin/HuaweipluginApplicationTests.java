package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.request.DataCollection;
import com.huaweiplugin.request.DeviceManagement;
import com.huaweiplugin.request.requestAuth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuaweipluginApplicationTests {

	@Autowired
	private requestAuth requestauth;

	@Autowired
	private DeviceManagement devicemanagement;

	@Autowired
	private DataCollection datacollection;

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
	}

	@Test
	public void refreshTest() throws Exception {

		HashMap datarefreshToken  = requestauth.refreshToken("211.25.75.100","8743","L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga",HuaweipluginApplicationTests.refreshtoken);
		assert datarefreshToken.get("state") != "HTTP/1.1 200 OK" : datarefreshToken.get("state");

		System.out.println("Old refreshToken : "+HuaweipluginApplicationTests.refreshtoken);
		System.out.println("New refreshToken : "+String.valueOf(datarefreshToken.get("refreshToken")));
	}

	@Test
	public void registerDeviceTest() throws Exception {

		HashMap responce = devicemanagement.regDirectDevice("xxxx-xxxx-xxxxx-xxxx");
		assert responce.get("state") != "HTTP/1.1 200 OK" : responce.get("state");
		System.out.println("verifyCode 	: "+ responce.get("verifyCode") );
		System.out.println("psk 		: "+ responce.get("psk") );
		System.out.println("deviceId 	: "+ responce.get("deviceId") );

	}

	@Test
	public void logoutTest() throws Exception {
		requestauth.logout();
	}

	@Test
	public void deleteTest() throws Exception {
		HashMap responce = devicemanagement.regDirectDevice("xxxx-xxxx-xxxxx-xxxx");
		assert responce.get("state") != "HTTP/1.1 200 OK" : responce.get("state");

		String deviceID = String.valueOf(responce.get("deviceId"));

		devicemanagement.deleteDirectDevice("211.25.75.100","8743", deviceID, "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a",HuaweipluginApplicationTests.accesstoken);
	}

	@Test
	public void activeStateTest() throws Exception {

		HashMap responce = devicemanagement.regDirectDevice("xxxx-xxxx-xxxxx-xxxx");
		assert responce.get("state") != "HTTP/1.1 200 OK" : responce.get("state");

		String deviceID = String.valueOf(responce.get("deviceId"));

		Map responceState = devicemanagement.activationStatus("211.25.75.100","8743", deviceID, "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a",HuaweipluginApplicationTests.accesstoken);
		System.out.println("deviceId 	: "+ responceState.get("deviceId") );
		System.out.println("activated 	: "+ responceState.get("activated") );

	}

	@Test
    public void deviceCapabilityTest() throws Exception {

        Map data = devicemanagement.deviceCapabilities("8de87b6b-0768-4cba-b900-51a9390e0e77");
        System.out.println(data);
    }

    @Test
    public void subcribeTest() throws Exception {
	    Map data = datacollection.subcribe("deviceInfoChanged");
        data = datacollection.subcribe("deviceAdded");
    }


}
