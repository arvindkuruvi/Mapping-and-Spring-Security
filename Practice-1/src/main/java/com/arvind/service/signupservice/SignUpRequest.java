package com.arvind.service.signupservice;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpRequest {

	@NotNull(message = "Name can't be null")
	@NotEmpty(message = "Name can't be empty")
	private String name;

	@NotNull(message = "phoneNumber can't be null")
	@NotEmpty(message = "phoneNumber  can't be empty")
	private String phone;

	@NotNull(message = "email id can't be null")
	@NotEmpty(message = "email id can't be empty")
	@Size(min = 5, max = 50, message = "Invalid length for email")
	@Pattern(message = "Invalid email id", regexp = "^.+@.+\\..+$")
	private String email;

	@NotNull(message = "Password can't be null")
	@NotEmpty(message = "Password can't be empty")
	private String password;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SignUpRequest() {}

	@Override
	public String toString() {
		return "SignUpRequest [name=" + name + ", phoneNumber=" + phone + ", email=" + email + ", password="
				+ password + "]";
	}

}
