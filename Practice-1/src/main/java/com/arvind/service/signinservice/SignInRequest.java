package com.arvind.service.signinservice;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInRequest {

	@NotNull(message = "email id can't be null")
	@NotEmpty(message = "email id can't be empty")
	@Size(min = 5, max = 50, message = "Invalid length for email")
	@Pattern(message = "Invalid email id", regexp = "^.+@.+\\..+$")
	private String email;

	@NotNull(message = "Password can't be null")
	@NotEmpty(message = "Password can't be empty")
	private String password;

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

	@Override
	public String toString() {
		return "SignInRequest [email=" + email + ", password=" + password + "]";
	}

}
