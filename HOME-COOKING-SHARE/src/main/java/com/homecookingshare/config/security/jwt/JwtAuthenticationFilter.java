package com.homecookingshare.config.security.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.homecookingshare.config.security.jwt.SimpleJwtTokenResolver.InvalidJwtTokenException;
import com.homecookingshare.config.security.jwt.api.JwtToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
	private final JwtTokenResolver jwtTokenResolver;
	private final JwtAuthenticationToken jwtAuthenticationToken;
	private final TokenStore tokenStore;
	private final String secretKey;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		JwtToken token = null;
		try {
			token = jwtTokenResolver.resolve((HttpServletRequest) request);
		} catch (InvalidJwtTokenException e) {
			chain.doFilter(request, response);
			return;
		}
		String accessToken = token.getAccessToken();
		
		log.info("token validation : {}", accessToken);
		if(JwtToken.isValid(accessToken, secretKey)) {
			String userPk = jwtAuthenticationToken.getUserPk(accessToken);
			Optional<JwtToken> findToken = tokenStore.findByUser(userPk);
			if(!findToken.isPresent()) {
				log.error("token not found : {}", accessToken);
				chain.doFilter(request, response);
				return;
			}
			if(!findToken.get().equalAccessToken(accessToken)) {
				log.error("not equal token : {}", accessToken);
				chain.doFilter(request, response);
				return;
			}
			log.info("authenticate success : {}", accessToken);
			processAuthenticate(accessToken);
		}
		chain.doFilter(request, response);
	}

	private void processAuthenticate(String accessToken) {
		Authentication authentication = jwtAuthenticationToken.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
