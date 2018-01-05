package com.huaweiplugin.huaweiplugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuaweipluginApplicationTests {

	@Autowired
	private requests request;

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
	}

	@Test
	public void refreshTest() throws Exception {

		HashMap datarefreshToken  = request.refreshToken("211.25.75.100","8743","L0sBaFLJKiXfdyXyxzPN1PMY5Q8a","fEL5Fjc8eIOR7Gj2pCzoC2E_voga",HuaweipluginApplicationTests.refreshtoken);
		assert datarefreshToken.get("state") != "HTTP/1.1 200 OK" : datarefreshToken.get("state");

		System.out.println("Old refreshToken : "+HuaweipluginApplicationTests.refreshtoken);
		System.out.println("New refreshToken : "+String.valueOf(datarefreshToken.get("refreshToken")));
	}

	@Test
	public void registerDeviceTest() throws Exception {

		HashMap responce = request.regDirectDevice("211.25.75.100","8743","L0sBaFLJKiXfdyXyxzPN1PMY5Q8a",HuaweipluginApplicationTests.accesstoken, "xxxx-xxxx-xxxxx-xxxx");
		assert responce.get("state") != "HTTP/1.1 200 OK" : responce.get("state");
		System.out.println("verifyCode 	: "+ responce.get("verifyCode") );
		System.out.println("psk 		: "+ responce.get("psk") );
		System.out.println("deviceId 	: "+ responce.get("deviceId") );

	}


}
