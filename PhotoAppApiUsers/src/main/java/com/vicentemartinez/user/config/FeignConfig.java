package com.vicentemartinez.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignConfig {
	@Bean
	public Logger.Level getLoggerLevel(){
		return Logger.Level.FULL;
	}
}
