package com.huaweiplugin.huaweiplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HuaweipluginApplication{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private requests request;

	public static void main(String[] args) {
		SpringApplication.run(HuaweipluginApplication.class, args);
	}
}
