package com.arvind.service.signupservice;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arvind.model.UserInfo;
import com.arvind.repository.UserRepo;
import com.arvind.service.MessageConstants;

@Service
public class SignUpServiceImpl implements SignUpService 
{
	@Autowired
	UserRepo customerRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public SignUpResponse execute(SignUpRequest request) {
		SignUpResponse response = new SignUpResponse();
		try {

			// validate customer already registered with this phone number
			this.validatePhoneExistOrNot(request.getPhone());

			// validate customer already registered with this email
			this.validateEmailExistOrNot(request.getEmail());

			// save the customer details in customer table
			this.saveCustomerDetails(request);

			// function to set success response
			this.setSuccessResponse(response);

		} catch (Exception e) {

			// function to set failure response
			this.setFailureResponse(response, e);

		}

		return response;

	}

	private void validatePhoneExistOrNot(String phoneNumber) throws Exception {
		boolean flag = customerRepository.existsByPhone(phoneNumber);
		if (flag) {
			
			throw new Exception(MessageConstants.PHONE_IN_USE);
		}

	}

	private void validateEmailExistOrNot(String email) throws Exception {
		boolean flag = customerRepository.existsByEmail(email);
		if (flag) {
			
			throw new Exception(MessageConstants.EMAIL_IN_USE);
		}

	}

	private void saveCustomerDetails(SignUpRequest request) {
		UserInfo customer = new UserInfo();
		
		customer.setPhone(request.getPhone());
		customer.setEmail(request.getEmail());
		customer.setPassword(passwordEncoder.encode(request.getPassword()));
		customer.setPhone(request.getPhone());
		customer.setName(request.getName());
		customerRepository.save(customer);
	}

	// function to set success response
	private SignUpResponse setSuccessResponse(SignUpResponse response) {
		
		response.setSuccessful(true);
		response.setMessage(MessageConstants.SAVED);
		return response;
	}

	// function to set failure response
	private SignUpResponse setFailureResponse(SignUpResponse response, Exception e) {
		
		response.setSuccessful(false);
		response.setMessage(e.getMessage());
		return response;
	}

}
