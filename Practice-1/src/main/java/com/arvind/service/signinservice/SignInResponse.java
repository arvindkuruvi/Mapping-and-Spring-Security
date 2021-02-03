package com.arvind.service.signinservice;

import com.arvind.service.CommonServiceResponse;

public class SignInResponse extends CommonServiceResponse {

	private String header;

	private String accessToken;

	private String tokenType;

	private String id;

	private String name;

	private String email;

	private String customerId;

	public SignInResponse() {
		super();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "SignInResponse [header=" + header + ", accessToken=" + accessToken + ", tokenType=" + tokenType
				+ ", id=" + id + ", name=" + name + ", email=" + email + ", customerId=" + customerId + "]";
	}

}
