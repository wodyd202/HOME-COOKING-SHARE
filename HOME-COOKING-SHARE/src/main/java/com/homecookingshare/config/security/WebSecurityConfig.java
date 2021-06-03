package com.homecookingshare.config.security;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.homecookingshare.config.security.jwt.JwtAuthenticationFilter;
import com.homecookingshare.config.security.jwt.JwtAuthenticationToken;
import com.homecookingshare.config.security.jwt.JwtTokenProvider;
import com.homecookingshare.config.security.jwt.JwtTokenResolver;
import com.homecookingshare.config.security.jwt.TokenStore;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtTokenResolver jwtTokenResolver;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Value("${spring.jwt.secretKey}")
	private String secretKey;

	@Value("${spring.jwt.accessTokenExpireAt}")
	private long accessTokenExpireAt;
	
	@Value("${spring.jwt.refreshTokenExpireAt}")
	private long refreshTokenExpireAt;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public JwtAuthenticationToken jwtAuthenticationToken() { 
		return new JwtAuthenticationToken(userDetailsService, secretKey);
	}
	
	@Bean
	public JwtTokenProvider jwtTokenProvider() {
		return new JwtTokenProvider(tokenStore,secretKey,accessTokenExpireAt,refreshTokenExpireAt);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.httpBasic().disable()
		.csrf().disable()
		.formLogin().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/api/v1/member").permitAll()
		.antMatchers(HttpMethod.PUT, "/api/v1/member/**").authenticated()
		.antMatchers(HttpMethod.GET, "/api/v1/member").permitAll()
		
		.antMatchers(HttpMethod.POST, "/api/v1/recipe").authenticated()
		.antMatchers(HttpMethod.PUT, "/api/v1/recipe/**").authenticated()
		.antMatchers(HttpMethod.DELETE, "/api/v1/recipe/**").authenticated()

		.antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
		.antMatchers(HttpMethod.POST, "/oauth/refresh-token").permitAll()
		.and()
		.exceptionHandling()
		.and()
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenResolver, jwtAuthenticationToken(), tokenStore, secretKey), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@PostConstruct
	public void setUp() {
		this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
	}
}
