package com.example.itenaryplanner.exception;

public class PasswordMismatchException extends RuntimeException {

	private static final long serialVersionUID = -2077685720803953122L;

	public PasswordMismatchException(String msg) {
		super(msg);
	}
}