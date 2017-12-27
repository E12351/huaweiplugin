package com.huaweiplugin.huaweiplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HuaweipluginApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private requests request;

	public static void main(String[] args) {
		SpringApplication.run(HuaweipluginApplication.class, args);
	}

	//	@Override
	public void run(String... strings) throws Exception {

//		request.login("172.22.172.157","8080","abcd","efgh");
//		request.regNoNDirectDevice("172.22.172.157","8080","abcd","efgh","asasas");
		request.deleteDirectDevice("172.22.172.157","8080","abcd","efgh","s","sdf");

		logger.info("Finished.");
	}
}
