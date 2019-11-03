package com.vicentemartinez.zuul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	@Autowired
	private Environment environment;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers(environment.getProperty("api.h2console.url.path")).permitAll() //Allow h2 console for dev purposes
		.antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll() //allow login service
		.antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll() //allow registration service
		.anyRequest().authenticated();//all other requests should be authenticated
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//NEVER create httpSession
	}
}
