package com.homecookingshare.config.security.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers()
			.frameOptions().disable()
			.and()
			.formLogin().disable();
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/api/v1/member").permitAll()
		.antMatchers(HttpMethod.PUT,"/api/v1/member").authenticated()
		.antMatchers(HttpMethod.POST,"/api/v1/member/auth").permitAll()
		.antMatchers(HttpMethod.GET,"/api/v1/member/auth").permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
}
