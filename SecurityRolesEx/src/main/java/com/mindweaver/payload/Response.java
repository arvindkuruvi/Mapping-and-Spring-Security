package com.mindweaver.payload;

public class Response 
{
	private boolean success = true;
	
	private String message;

	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", message=" + message + "]";
	}
	
	
}
