package com.pji.user.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}