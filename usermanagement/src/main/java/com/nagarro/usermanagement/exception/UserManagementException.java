package com.nagarro.usermanagement.exception;

public class UserManagementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886160725597525262L;

	private String msg;

	public UserManagementException(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
}
