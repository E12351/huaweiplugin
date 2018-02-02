package com.huaweiplugin.huaweiplugin;


import com.huaweiplugin.Parameter.AuthHandle;
import com.huaweiplugin.Parameter.Constant;
import com.huaweiplugin.request.requestAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan({"com.huaweiplugin"})
@EntityScan({"com.huaweiplugin"})
@EnableJpaRepositories("com")
@EnableScheduling
public class Application implements CommandLineRunner{

	@Autowired
	public AuthHandle authHandle;

	@Autowired
	private requestAuth requestauth;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Huawei Interaction Service started.");
		HashMap data = requestauth.login(Constant.URL,Constant.PORT, Constant.APPID,Constant.SECRET);
		String accessToken = String.valueOf(data.get("accessToken"));
		String refreshToken = String.valueOf(data.get("refreshToken"));

		authHandle.setaccessToken(accessToken);
		authHandle.setrefreshToken(refreshToken);

		log.info("accessToken : {}  refreshToken : {}",accessToken,refreshToken);

	}

}
