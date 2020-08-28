package com.hansung.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class BootSecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootSecurityApplication.class, args);
	}
}