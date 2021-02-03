package com.arvind.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arvind.model.Customer;
import com.arvind.service.CommonServiceResponse;
import com.arvind.service.signinservice.SignInRequest;
import com.arvind.service.signinservice.SignInResponse;
import com.arvind.service.signinservice.SignInService;
import com.arvind.service.signupservice.SignUpRequest;
import com.arvind.service.signupservice.SignUpResponse;
import com.arvind.service.signupservice.SignUpService;

@RestController
@RequestMapping("/api")
public class RegistrationController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	SignUpService signUpService;

	@Autowired
	SignInService signInService;

	// to register the customer details in customer  table
	@PostMapping(value = "/signUp")
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
//		LOGGER.info("<-------Input request for  /signUp----->:{}", request);
		SignUpResponse response = signUpService.execute(request);
//		LOGGER.info("Response for  /signUp:{}", response);
		return ResponseEntity.ok().body(response);
	}
	
	
	// to login as a customer
	@PostMapping(value = "/signIn")
	public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {			
//		LOGGER.info("<-------Input request for  /signIn----->:{}", request);
		SignInResponse response = signInService.execute(request);
//		LOGGER.info("Response for  /signIn:{}", response);
		return ResponseEntity.ok().body(response);
	}

	
	@GetMapping(value = "/hello")
	public ResponseEntity<CommonServiceResponse> hello() 
	{	
		CommonServiceResponse response = new CommonServiceResponse();
		response.setMessage("Hello World!");
		response.setSuccessful(true);
		return ResponseEntity.ok().body(response);
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<CommonServiceResponse> getUsers() 
	{	
		List<Customer> users = new ArrayList<Customer>();
		
		users.add(new Customer("Arvind" , 23 , "MALE"));
		users.add(new Customer("Vishwas" , 49 , "MALE"));
		users.add(new Customer("Punith" , 90 , "MALE"));
		users.add(new Customer("Ramya" , 89 , "FEMALE"));
		users.add(new Customer("Anitha" , 123 , "FEMALE"));
		users.add(new Customer("Chethan" , 70 , "MALE"));
		users.add(new Customer("Vaishnavi" , 36 , "FEMALE"));
		users.add(new Customer("Abhi" , 83 , "MALE"));
		users.add(new Customer("Geetha" , 7 , "FEMALE"));
		users.add(new Customer("Reshma" , 17 , "FEMALE"));
		users.add(new Customer("Sanjith" , 38 , "MALE"));
		
		CommonServiceResponse response = new CommonServiceResponse();
		
		// Filtering only the females from the List
		List<String> females = users.stream()
										.filter( user -> user.getGender().equals("FEMALE"))
										.map( user -> user.getName())
										.collect(Collectors.toList());
		
//		response.setMessage("Only Females " + females);
		
		
		// Sorting In ascending Order
		List<String> names = users.stream()
							.sorted(Comparator.comparing( Customer::getName ))
							.map( user -> user.getName())
							.collect(Collectors.toList());
//		response.setMessage("Names in ASC " + names);
		
		String maxAge = users.stream()
							.max(Comparator.comparing( Customer::getAge ))
							.map( user -> user.getName()).toString();
		
		response.setMessage("Oldest Person in List " + maxAge);
		

		response.setSuccessful(true);
		return ResponseEntity.ok().body(response);
	}
}
