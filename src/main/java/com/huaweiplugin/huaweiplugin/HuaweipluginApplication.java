package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

@SpringBootApplication
@EnableScheduling
public class HuaweipluginApplication implements CommandLineRunner{

	@Autowired
	private AuthHandle authHandle;

	@Autowired
	private requests request;

	private static final Logger log = LoggerFactory.getLogger(HuaweipluginApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HuaweipluginApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Huawei Interaction Service started.");

		HashMap data = request.login(Constant.URL,Constant.PORT, Constant.APPID,Constant.SECRET);
		String accessToken = String.valueOf(data.get("accessToken"));
		String refreshToken = String.valueOf(data.get("refreshToken"));

		authHandle.setaccessToken(accessToken);
		authHandle.setrefreshToken(refreshToken);

		log.info("accessToken : {}  refreshToken : {}",accessToken,refreshToken);

	}
}
