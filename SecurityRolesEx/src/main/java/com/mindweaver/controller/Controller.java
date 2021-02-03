package com.mindweaver.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindweaver.domain.Customer;
import com.mindweaver.domain.Role;
import com.mindweaver.payload.Request;
import com.mindweaver.payload.Response;
import com.mindweaver.repository.CustomerRepo;
import com.mindweaver.repository.RoleRepo;
import com.mindweaver.security.JwtTokenProvider;

@RestController
@RequestMapping("/")
public class Controller {
	
	@Autowired
	CustomerRepo custRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/role")
	public ResponseEntity<Response> addRole(@RequestBody Request role)
	{
		Response response = new Response();
		if (roleRepo.findByRoleName(role.getRole().toUpperCase()) == null )
		{
			Role r = new Role();
			r.setRoleName(role.getRole().toUpperCase());
			
			roleRepo.save(r);
			
			response.setSuccess(true);
			response.setMessage("Saved Successfully");
		}
		else {
			response.setSuccess(false);
			response.setMessage("Role Already present!");
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/role")
	public List<Role> getRole()
	{
		return roleRepo.findAll();
	}
	
	@PostMapping("/cust")
	public ResponseEntity<Response> addAdmin(@RequestBody Request request)
	{
		Response response = new Response();
		
		if (roleRepo.findByRoleName(request.getRole().toUpperCase()) != null )
		{
			if(custRepo.existsByPhone(request.getPhone()) && custRepo.existsByEmail(request.getEmail()))
			{
				response.setSuccess(true);
				response.setMessage("Admin with Phone/Email already exists");
			}
			else {
				System.out.println(request);
				Customer admin = new Customer();
				admin.setEmail(request.getEmail());
				admin.setName(request.getName());
				admin.setPhone(request.getPhone());
				admin.setPassword(encoder.encode(request.getPassword()));
				
				
				admin = custRepo.save(admin);
				
				Set<Role> rolList = custRepo.findAllRoleByUserId(roleRepo.findByRoleName(request.getRole().toUpperCase()).getId());
				rolList.add(roleRepo.findByRoleName(request.getRole().toUpperCase()));
				
				admin.setRoles(rolList);
				custRepo.save(admin);
				
				System.out.println(custRepo.findById(admin.getId()));
				response.setSuccess(true);
				response.setMessage("Saved Successfully");
			}
		}
		else {
			response.setSuccess(false);
			response.setMessage("Unexpected Role!");
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/helloAdmin")
	public ResponseEntity<Response> helloWorldAdmin()
	{
		Response response = new Response();
		response.setSuccess(false);
		response.setMessage("Hello World! , Admin");
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/helloUser")
	public ResponseEntity<Response> helloWorldUser()
	{
		Response response = new Response();
		response.setSuccess(false);
		response.setMessage("Hello World! , User");
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<Response> SigIn(@RequestBody Request request)
	{
		System.out.println("entered signIn");
		System.out.println(request);
		Response response = new Response();
		
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if (authentication == null) {
			response.setSuccess(false);
			response.setMessage("Authentication Failed!");
		}
		
		
		if (authentication != null && authentication.isAuthenticated()) {
			Customer customer = custRepo.findByEmail(request.getEmail());
			if (customer == null) {
				response.setSuccess(false);
				response.setMessage("Customer Not Found!");
			}
			else
			{
				String jwt = this.tokenProvider.generateToken(authentication);
				response.setSuccess(true);
				response.setMessage(jwt);
			}
		}
		System.out.println("End of signIn");
		return ResponseEntity.ok().body(response);
	}
}
