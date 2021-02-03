package com.mindweaver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		// we need to change here for user
		if (uri.endsWith("role") || uri.endsWith("signIn") || uri.endsWith("cust")) {
			logger.info("uri of registration: {}", uri);
			filterChain.doFilter(request, response);
		} else if (uri.endsWith("helloAdmin") || uri.endsWith("helloUser") ) {
			try {
				logger.info("uri is : {}", uri);
				String jwt = getJwtFromRequest(request);

				if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
					String userId = tokenProvider.getUserIdFromJWT(jwt);

					UserDetails userDetails = customUserDetailsService.loadUserById(userId);
					System.out.println(userDetails);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					System.out.println("Almost end of do filter");
					filterChain.doFilter(request, response);
				} else {
					logger.info("Local JWT is NOT AUTHORIZED");
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Local JWT is not AUTHORIZED");
				}

			} catch (Exception ex) {
				logger.error("Could not set user authentication in security context", ex);
			}
		} else {
			logger.info("uri is : {}", uri);
			logger.info("Local JWT is not AUTHORIZED");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Local JWT is not AUTHORIZED");
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}