package com.arvind.service;

//common response for all api
public class CommonServiceResponse {
	public boolean successful = true;

	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	@Override
	public String toString() {
		return "CommonServiceResponse [successful=" + successful + ", message=" + message + "]";
	}

}
