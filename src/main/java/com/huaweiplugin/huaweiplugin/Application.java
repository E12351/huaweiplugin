package com.huaweiplugin.huaweiplugin;


import com.huaweiplugin.Utils.Constant;
import com.huaweiplugin.services.requests;
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
@EnableJpaRepositories("com")
@EnableScheduling
public class Application implements CommandLineRunner{

	@Autowired
	public AuthHandle authHandle;

	@Autowired
	private requests request;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
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
