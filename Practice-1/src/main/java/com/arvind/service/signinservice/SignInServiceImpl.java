package com.arvind.service.signinservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arvind.constants.Constants;
import com.arvind.model.UserInfo;
import com.arvind.repository.UserRepo;
import com.arvind.security.JwtTokenProvider;
import com.arvind.service.MessageConstants;

@Service
public class SignInServiceImpl implements SignInService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignInServiceImpl.class);

	@Autowired
	UserRepo customerRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;

	@Override
	public SignInResponse execute(SignInRequest request) {
		SignInResponse response = new SignInResponse();
		try {

			// to check customer registered with this email
			this.validateEmailExistOrNot(request.getEmail());

			//  to check email and passord is correct
			Authentication authentication = this.checkAuth(request);

			// to get the customer details from customer table if authenticated
			UserInfo customer = this.getUserDetails(authentication, request);

			// function to set success response
			this.setSuccessResponse(response, customer, authentication);

		} catch (Exception e) {

			// function to set failure response
			this.setFailureResponse(response, e);

		}

		return response;
	}

	private void validateEmailExistOrNot(String email) throws Exception {
		boolean flag =  customerRepository.existsByEmail(email);
		if (!flag) {
			LOGGER.error("EMAIL_NOT_FOUND");
			throw new Exception(MessageConstants.EMAIL_NOT_FOUND);
		}
	}
	
	private Authentication checkAuth(SignInRequest request) throws Exception {
		// to check login credentials are correct
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if (authentication == null) {
			LOGGER.error("wrong email or password");
			throw new Exception(MessageConstants.SIGNIN_FAILED);
		}
		return authentication;
	}

	private UserInfo getUserDetails(Authentication authentication, SignInRequest request) throws Exception {
		 UserInfo customer = null;
		// if customer is authenticated, fetch customer details From customer table
		if (authentication != null && authentication.isAuthenticated()) {
			customer = customerRepository.findByEmail(request.getEmail());
			if (customer == null) {
				LOGGER.error(" CUSTOMER_NOT_FOUND");
				throw new Exception(MessageConstants.CUSTOMER_NOT_FOUND);
			}
		}
		return customer;
	}
	
	private SignInResponse setSuccessResponse(SignInResponse response, UserInfo customer, Authentication authentication) {
		LOGGER.info("LOGIN_SUCCESS");
		response.setSuccessful(true);
		response.setMessage(MessageConstants.SIGNIN_SUCCESS);

		// to generate JWT token for the customer
		String jwt = this.tokenProvider.generateToken(authentication);
		response.setAccessToken(jwt);
		response.setHeader(Constants.HEADER_NAME);
		response.setTokenType(Constants.TOKEN_TYPE);
		response.setCustomerId(customer.getPhone());
		response.setEmail(customer.getEmail());
		response.setId(customer.getId().toString());
		response.setName(customer.getName());
		return response;
	}
	
	// function to set failure response
		private SignInResponse setFailureResponse(SignInResponse response, Exception e) {
			LOGGER.error("LOGIN failed");
			response.setSuccessful(false);
			response.setMessage(e.getMessage());
			return response;
		}
}
